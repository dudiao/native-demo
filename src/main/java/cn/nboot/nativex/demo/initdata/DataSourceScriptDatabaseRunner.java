package cn.nboot.nativex.demo.initdata;

import cn.nboot.nativex.demo.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author songyinyin
 * @since 2022/3/19 00:46
 */
@Slf4j
@Component
public class DataSourceScriptDatabaseRunner implements ApplicationRunner {
  @Autowired
  private DataSource dataSource;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // 执行SQL脚本
    log.info("start execute sql script.");
    StopWatch stopWatch = new StopWatch();

    stopWatch.start("Get Current Version");
    // 初始化表结构
    this.initMetaTable();
    // 获取已经执行的SQL版本
    long databaseVersion = this.getDatabaseVersion();
    stopWatch.stop();

    stopWatch.start("Remove Low Version Script");
    List<Resource> resources = getSqlResources();
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
    if (resources.isEmpty()) {
      log.info("sql version is up to date.");
      stopWatch.stop();
      return;
    }
    resources.sort((Comparator.comparingInt(o -> getVersion(o.getFilename()))));
    stopWatch.stop();

    for (Resource resource : resources) {
      stopWatch.start("execute sql " + resource.getFilename());
      ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
      populator.setContinueOnError(true);
      populator.addScript(resource);
      populator.setSqlScriptEncoding("UTF-8");
      DatabasePopulatorUtils.execute(populator, this.dataSource);
      stopWatch.stop();
      long cost = stopWatch.getLastTaskTimeMillis();
      log.debug("execute sql scripts: {}, took {} ms", resource.getFilename(), TimeUtils.getTimeAbbreviate(cost));

      int update = jdbcTemplate.update("insert into sql_version_record " +
              "(sql_name, sql_version, sql_cost, sql_exe_time) values ( ?, ?, ?, CURRENT_TIMESTAMP)",
          resource.getFilename(), getVersion(resource.getFilename()), cost);
      if (update != 1) {
        log.error("insert sql version fail, sql name is [{}]", resource.getFilename());
      }
    }
    log.info("sql scripts count: {}, took {}.", resources.size(), TimeUtils.getTimeAbbreviate(stopWatch.getTotalTimeNanos()));
  }

  private List<Resource> getSqlResources() {
    return Stream.of("initsql/V1__nd_user.sql")
        .map(ClassPathResource::new)
        .collect(Collectors.toList());
  }

  /**
   * 从数据库中查出当前 sqlType 的版本
   *
   * @return 数据库中的SQL版本号
   */
  private long getDatabaseVersion() {
    Long sqlVersion = jdbcTemplate.queryForObject(
        "select sql_version from sql_version_record ORDER BY sql_version DESC LIMIT 1", Long.class);
    if (sqlVersion == null) {
      return 0;
    }
    return sqlVersion;
  }

  /**
   * 初始化SQL版本信息表，并插入一条默认数据
   */
  private void initMetaTable() {
    String metaTableSql = "create table if not exists sql_version_record " +
        "(sql_name varchar(32) NOT NULL, " +
        "sql_version int8 NOT NULL DEFAULT 0, " +
        "sql_cost int8 NOT NULL DEFAULT 0, " +
        "sql_exe_time timestamp NULL)";
    jdbcTemplate.execute(metaTableSql);
    jdbcTemplate.update("insert into sql_version_record " +
        "(sql_name, sql_version, sql_cost, sql_exe_time) values ( 'init data', 0, 0, CURRENT_TIMESTAMP)");
  }

  /**
   * 从文件名中获取版本号
   *
   * @param filename 文件名
   * @return 版本号
   */
  private int getVersion(String filename) {
    String[] fileNames = Objects.requireNonNull(filename).split("__");
    return Integer.parseInt(fileNames[0].substring(1));
  }

}
