package es.netmind.mypersonalbankapi.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = {"es.netmind.mypersonalbankapi.persistencia", "es.netmind.mypersonalbankapi.controladores"})
@EntityScan("es.netmind.mypersonalbankapi.modelos")
public class SpringConfig {


}