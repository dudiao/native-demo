package cn.nboot.nativedemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author songyinyin
 */
@RestController
public class IndexController {

  @GetMapping("/")
  public String index() {
    return String.format("success! time %s", LocalDateTime.now());
  }

  @GetMapping("/{msg}")
  public String msg(@PathVariable(value = "msg", required = false)
                            String msg) {
    return String.format("success! time %s, msg: %s", LocalDateTime.now(), msg);
  }


}
