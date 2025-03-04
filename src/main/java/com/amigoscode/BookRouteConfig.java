package com.amigoscode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class BookRouteConfig {

    public record Book (String Name, String Auther, Integer id) {
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){

        BookHandler bookHandler = new BookHandler();
        return RouterFunctions.route()
                .GET("/api/v1/books",bookHandler::getAllBooks) // bookHandler::getAllBooks == bookHandler.getAllBooks(request))
                .GET("/api/v1/books/{id}",bookHandler::getBookById)
                .build();
    }


}
