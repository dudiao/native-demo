package cn.nboot.nativedemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class NativeDemoApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  public void test() {
    String version = "0001";
    int i = Integer.parseInt(version);
    System.out.println(i);

    AtomicInteger index = new AtomicInteger(0);
    System.out.println("getAndIncrement = " + index.getAndIncrement());
    System.out.println(index.get());
  }

}
