package com.sisllc.instaiml;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/*

https://github.com/oktadev/okta-spring-boot-react-crud-example


https://developer.okta.com/blog/2022/06/17/simple-crud-react-and-spring-boot
https://github.com/taniarascia/react-hooks
https://www.taniarascia.com/crud-app-in-react-with-hooks/

https://github.com/javaugi/fullstack-nextjs-app-template
https://github.com/javaugi/full-stack-with-react-and-spring-boot

// */
@Slf4j
@EnableConfigurationProperties
@ComponentScan(basePackages = MyReactiveApplication.PACKAGE_SCAN_BASE)
@SpringBootApplication
public class MyReactiveApplication {
    protected static final String PACKAGE_SCAN_BASE = "com.sisllc.instaiml";    
    
    //com.sisllc.instaiml.MyReactiveApplication
	public static void main(String[] args) {
        System.setProperty("AZURE_CLIENT_TELEMETRY_DISABLED", "true");
        //See AzureVMMetadataTelemetry.md
		SpringApplication.run(MyReactiveApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Beans provided by Spring Boot {}", SpringBootVersion.getVersion());
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.debug(beanName);
			}
		};
	}
}
