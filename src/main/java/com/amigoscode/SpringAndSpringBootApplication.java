package com.amigoscode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class SpringAndSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(
                SpringAndSpringBootApplication.class,
                args
        );

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
        }
        System.out.println(beanDefinitionNames.length);
    }

    @Bean
    @Primary
    public String redBean(){
        return "redBean";
    }

    @Bean
    public String BlueBean(){
        return "BlueBean";
    }

    @Bean
    CommandLineRunner CommandLineRunner (String redBean, String blueBean) {
        return args -> {
            System.out.println("Hello From commandLineRunner 1");
            System.out.println(redBean);
            System.out.println(blueBean);
        };
    }

    @Bean
    CommandLineRunner CommandLineRunner2 (String redBean, String blueBean) {
        return args -> {
            System.out.println("Hello From commandLineRunner 2");
            System.out.println(redBean);
            System.out.println(blueBean);
        };
    }
}
