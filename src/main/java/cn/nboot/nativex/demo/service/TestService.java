package cn.nboot.nativex.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author songyinyin
 * @since 2022/5/15 22:06
 */
@Service
public class TestService implements ITestService{

  @Override
  public String sayHello() {
    return "Hello baby.";
  }
}
