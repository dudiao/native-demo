package cn.nboot.nativedemo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.io.File;

/**
 * @author songyinyin
 */
@Slf4j
public class ApplicationStartingListener implements ApplicationListener<ApplicationPreparedEvent> {

  @Override
  public void onApplicationEvent(ApplicationPreparedEvent event) {
    File file = new File("db");
    if (!file.exists()) {
//      file.mkdirs();
      log.info("create db path: {}", file.getAbsolutePath());
    }
  }
}
