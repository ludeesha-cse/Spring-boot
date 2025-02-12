package com.amigoscode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    CommandLineRunner CommandLineRunner (String redBean, String blueBean, UserService userService) {
        return args -> {
            System.out.println("Hello From commandLineRunner 1");
            System.out.println(redBean);
            System.out.println(blueBean);
            System.out.println(userService.getUsers());
            System.out.println(userService.getUserById(2));
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

    public record User(int id, String name) {

    }

    @Component
    public class UserService {
        public List<User> getUsers() {
            return List.of(
                    new User(1, "James"),
                    new User(2, "Maria"),
                    new User(3, "Anna")
            );
        }

        public Optional<User> getUserById(int id) {
            return getUsers().stream()
                    .filter(user -> user.id() == id)
                    .findFirst();
        }


    }
}
