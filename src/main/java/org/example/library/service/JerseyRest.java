package org.example.library.service;

import org.example.library.domain.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/book")
public class JerseyRest {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book(1,"Война и мир", "Лев Толстой", "Стеллаж 1, Полка 2", false, false));
        books.add(new Book(2,"Преступление и наказание", "Федор Достоевский", "Стеллаж 2, Полка 1", false, true));
        books.add(new Book(3,"1984", "Джордж Оруэлл", "Стеллаж 3, Полка 3", true, false));
    }

    @GET
    @Path("/list")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getBooks() {
        GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(books) {};
        return Response.ok(entity).build();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String msg() {
        return "Hello, new user!";
    }

    @GET
    @Path("/fetch/{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response fetch(@PathParam("id") String id) {
        Optional<Book> book = books.stream().filter(b -> b.getTitle().equals(id)).findFirst();
        if (book.isPresent()) {
            return Response.ok(book.get()).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        books.add(book);
        String result = "Book saved: " + book.getTitle();
        return Response.status(201).entity(result).build();
    }
}