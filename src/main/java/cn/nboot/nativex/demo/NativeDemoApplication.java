package cn.nboot.nativex.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import xyz.erupt.core.annotation.EruptScan;

@EruptScan
@EntityScan
@SpringBootApplication
public class NativeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NativeDemoApplication.class, args);
	}

}
