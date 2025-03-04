package com.amigoscode;

import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.stream.Stream;

public class BookHandler {

    public ServerResponse getAllBooks(ServerRequest request) {
        return ServerResponse.ok().body(
                List.of(new BookRouteConfig.Book("Harry Potter", "J.K. Rowling",1))
        );
    }

    public ServerResponse getBookById(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(
                Stream.of(
                        new BookRouteConfig.Book("Harry Potter", "J.K. Rowling",1),
                        new BookRouteConfig.Book("Harry Potter 2", "J.K. Rowling",2),
                        new BookRouteConfig.Book("Harry Potter 3", "J.K. Rowling",3)
                        ).filter( book -> book.id().equals(id))
        );
    }
}
