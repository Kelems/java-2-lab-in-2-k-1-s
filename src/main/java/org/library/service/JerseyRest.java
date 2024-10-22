package org.library.service;

import org.library.domain.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/book")
public class JerseyRest {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book(1, "Война и мир", "Лев Толстой", "Стеллаж 1, Полка 2", false, false));
        books.add(new Book(2, "Преступление и наказание", "Федор Достоевский", "Стеллаж 2, Полка 1", false, true));
        books.add(new Book(3, "1984", "Джордж Оруэлл", "Стеллаж 3, Полка 3", true, false));
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBooks() {
        GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(books) {};
        return Response.ok(entity).build();
    }

    @GET
    @Path("/search/title")
    @Produces({MediaType.APPLICATION_JSON})
    public Response searchByTitle(@QueryParam("title") String title) {
        List<Book> result = books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
        GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(result) {};
        return Response.ok(entity).build();
    }

    @GET
    @Path("/search/author")
    @Produces({MediaType.APPLICATION_JSON})
    public Response searchByAuthor(@QueryParam("author") String author) {
        List<Book> result = books.stream()
                .filter(book -> book.getAuthor().contains(author))
                .collect(Collectors.toList());
        GenericEntity<List<Book>> entity = new GenericEntity<List<Book>>(result) {};
        return Response.ok(entity).build();
    }

    @GET
    @Path("/fetch/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response fetch(@PathParam("id") int id) {
        Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();
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
        String result = "Книга внесена: " + book.getTitle();
        return Response.status(201).entity(result).build();
    }
}