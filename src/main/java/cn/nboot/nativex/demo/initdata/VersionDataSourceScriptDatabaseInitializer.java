package cn.nboot.nativex.demo.initdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer;
import org.springframework.core.io.Resource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 带有版本的SQL初始化
 *
 * @author songyinyin
 */
@Slf4j
@Component
public class VersionDataSourceScriptDatabaseInitializer extends SqlDataSourceScriptDatabaseInitializer {

  /**
   * 使用执行顺序来判断执行的SQL类型
   *
   * @see AbstractScriptDatabaseInitializer#initializeDatabase()
   */
  private final AtomicLong index = new AtomicLong(0);

  private final JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

  public VersionDataSourceScriptDatabaseInitializer(DataSource dataSource, SqlInitializationProperties properties) {
    super(dataSource, properties);
  }

  /**
   * 不执行低版本的 SQL 文件
   */
  @Override
  protected void runScripts(List<Resource> resources, boolean continueOnError, String separator, Charset encoding) {

    if (CollectionUtils.isEmpty(resources)) {
      return;
    }

    StopWatch stopWatch = new StopWatch();

    stopWatch.start("Get Current Version");
    String sqlType = SqlType.getSqlType(index.getAndIncrement()).getType();
    // 初始化表结构
    this.initMetaTable();
    // 获取已经执行的SQL版本
    long databaseVersion = this.getDatabaseVersion(sqlType);
    stopWatch.stop();

    stopWatch.start("Remove Low Version Script");
    Iterator<Resource> iterator = resources.iterator();
    long maxVersion = databaseVersion;
    while (iterator.hasNext()) {
      Resource next = iterator.next();
      int version;
      try {
        version = getVersion(next.getFilename());
      } catch (Exception e) {
        // SQL脚本名称不规范的，移除
        iterator.remove();
        log.warn("file name is not valid, ignore run sql script [{}]", next.getFilename());
        continue;
      }
      // 移除 已经执行过的SQL脚本
      if (databaseVersion >= version) {
        iterator.remove();
      }
      maxVersion = Math.max(maxVersion, version);
    }
    resources.sort((Comparator.comparingInt(o -> getVersion(o.getFilename()))));
    stopWatch.stop();

    stopWatch.start("Run Scripts");
    super.runScripts(resources, continueOnError, separator, encoding);
    stopWatch.stop();

    stopWatch.start("Update Version");
    int update = jdbcTemplate.update("update sql_version_record set sql_version = ? where sql_type = ?", maxVersion, sqlType);
    if (update != 1) {
      log.error("update sql version fail, sql type is [{}]", sqlType);
    }
    stopWatch.stop();
    log.info("execute [{}] sql scripts: {}, took {} ms", sqlType, resources.size(), stopWatch.getTotalTimeMillis());
    if (log.isDebugEnabled()) {
      log.debug("detail info: \n{}", stopWatch.prettyPrint());
    }
  }

  /**
   * 从数据库中查出当前 sqlType 的版本
   *
   * @param sqlType SQL类型：schema, data
   * @return 数据库中的SQL版本号
   * @see SqlType
   */
  private long getDatabaseVersion(String sqlType) {
    List<Map<String, Object>> maps = jdbcTemplate.queryForList(
        "select sql_type, sql_version from sql_version_record where sql_type = ?", sqlType);
    long databaseVersion;
    if (CollectionUtils.isEmpty(maps)) {
      // 增加该类型的初始化数据
      jdbcTemplate.update("insert into sql_version_record (sql_type, sql_version) values (?, ?)", sqlType, 0);
      log.info("insert sql version, sql type is {}", sqlType);
      databaseVersion = 0;
    } else if (maps.size() == 1) {
      databaseVersion = Long.parseLong(maps.get(0).get("sql_version").toString());
    } else {
      log.warn("sqlType [{}] has multiple rows", sqlType);
      throw new IncorrectResultSizeDataAccessException(1, maps.size());
    }
    return databaseVersion;
  }

  /**
   * 初始化SQL版本信息表
   */
  private void initMetaTable() {
    String metaTableSql = "create table if not exists sql_version_record " +
        "(sql_type varchar(32) NOT NULL PRIMARY KEY, " +
        "sql_version int8 NOT NULL DEFAULT 0)";
    jdbcTemplate.execute(metaTableSql);
  }

  /**
   * 从文件名中获取版本号
   *
   * @param filename 文件名
   * @return 版本号
   */
  private int getVersion(String filename) {
    String[] fileNames = Objects.requireNonNull(filename).split("_");
    return Integer.parseInt(fileNames[0]);
  }

}
