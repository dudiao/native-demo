package cn.nboot.nativex.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author songyinyin
 */
@RestController
public class IndexController {

  @GetMapping("/time")
  public String index() {
    return String.format("success! time %s", LocalDateTime.now());
  }

  @GetMapping("/msg/{msg}")
  public String msg(@PathVariable(value = "msg", required = false)
                        String msg) {
    return String.format("success! time %s, msg: %s", LocalDateTime.now(), msg);
  }

  @GetMapping("/ids")
  public String ids(@RequestBody List<String> ids) {
    return String.format("success!ids: %s", ids);
  }

}
