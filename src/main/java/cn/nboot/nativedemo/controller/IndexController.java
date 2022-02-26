package cn.nboot.nativedemo.controller;

import cn.nboot.nativedemo.initdata.VersionDataSourceScriptDatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author songyinyin
 */
@RestController
public class IndexController {

  @Autowired
  private VersionDataSourceScriptDatabaseInitializer versionDataSourceScriptDatabaseInitializer;

  @GetMapping("/")
  public String index() {
    return String.format("success! time %s", LocalDateTime.now());
  }

  @GetMapping("/msg/{msg}")
  public String msg(@PathVariable(value = "msg", required = false)
                        String msg) {
    return String.format("success! time %s, msg: %s", LocalDateTime.now(), msg);
  }

  @GetMapping("/init-data")
  public String initData() {
    boolean result = versionDataSourceScriptDatabaseInitializer.initializeDatabase();
    return String.format("schema and data result = %s", result);
  }

}
