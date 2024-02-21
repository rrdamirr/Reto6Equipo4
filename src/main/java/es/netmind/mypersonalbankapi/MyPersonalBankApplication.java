package es.netmind.mypersonalbankapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyPersonalBankApplication {
    private static final Logger logger = LoggerFactory.getLogger(MyPersonalBankApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyPersonalBankApplication.class, args);
    }

}
