package cn.nboot.nativex.demo.initdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;

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

    stopWatch.start("execute sql script");
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.setContinueOnError(true);
    String sql = "initsql/V1__nd_user.sql";
    ClassPathResource resource = new ClassPathResource(sql);
    populator.addScript(resource);
    DatabasePopulatorUtils.execute(populator, this.dataSource);
    stopWatch.stop();

    log.info("execute sql scripts: {}, took {} ms", sql, stopWatch.getTotalTimeMillis());
  }

}
