package org.library.service;

import org.library.domain.Book;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

public class BookRest {
    private static final int PORT = 8080;
    private static final int OK = 200;
    private static final int NOT_ALLOWED = 405;

    private static List<Book> books = new ArrayList<>();

    static {
        // Пример инициализации списка книг
        books.add(new Book(1, "Война и мир", "Лев Толстой", "Стеллаж 1, Полка 2", false, false));
        books.add(new Book(2, "Преступление и наказание", "Федор Достоевский", "Стеллаж 2, Полка 3", false, true));
        books.add(new Book(3, "1984", "Джордж Оруэлл", "Стеллаж 3, Полка 1", true, false));
    }

    public static void main(String[] args) {
        try {
// Вывод всех книг
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.createContext("/book/list", httpExchange -> {
                if ("GET".equals(httpExchange.getRequestMethod())) {
                    httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                    httpExchange.sendResponseHeaders(OK, 0);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(baos, books);
                    byte [] response = baos.toByteArray();
                    OutputStream responseBody = httpExchange.getResponseBody();
                    responseBody.write(response);
                    responseBody.close();
                } else {
                    httpExchange.sendResponseHeaders(NOT_ALLOWED, -1);
                }
            });

// Поиск по названию
            httpServer.createContext("/book/search/title", httpExchange -> {
                if ("GET".equals(httpExchange.getRequestMethod())) {
                    //находит по вписанному книгу
                    //чувствителен к регистру
                    String query = httpExchange.getRequestURI().getQuery();
                    String title = query.split("=")[1];
                    List<Book> result = books.stream()
                            .filter(book -> book.getTitle().contains(title))
                            .collect(Collectors.toList());
                    //Используем результат поиска по аналогии с выводом всех книг
                    httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                    httpExchange.sendResponseHeaders(OK, 0);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(baos, result);
                    byte [] response = baos.toByteArray();
                    OutputStream responseBody = httpExchange.getResponseBody();
                    responseBody.write(response);
                    responseBody.close();
                } else {
                    httpExchange.sendResponseHeaders(NOT_ALLOWED, -1);
                }
            });

// Поиск по автору
            httpServer.createContext("/book/search/author", httpExchange -> {
                if ("GET".equals(httpExchange.getRequestMethod())) {
                    //находит по вписанному автора
                    //чувствителен к регистру
                    String query = httpExchange.getRequestURI().getQuery();
                    String author = query.split("=")[1];
                    List<Book> result = books.stream()
                            .filter(book -> book.getAuthor().contains(author))
                            .collect(Collectors.toList());
                    //Используем результат поиска по аналогии с выводом всех книг
                    httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                    httpExchange.sendResponseHeaders(OK, 0);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(baos, result);
                    byte [] response = baos.toByteArray();
                    OutputStream responseBody = httpExchange.getResponseBody();
                    responseBody.write(response);
                    responseBody.close();
                } else {
                    httpExchange.sendResponseHeaders(NOT_ALLOWED, -1);
                }
            });

// Поиск по id
            httpServer.createContext("/book/fetch", httpExchange -> {
                if ("GET".equals(httpExchange.getRequestMethod())) {
                    String path = httpExchange.getRequestURI().getPath();
                    String id = path.substring(path.lastIndexOf('/') + 1);
                    Optional<Book> book = books.stream().filter(b -> b.getId().toString().equals(id)).findFirst();
                    if (book.isPresent()) {
                        httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                        httpExchange.sendResponseHeaders(OK, 0);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(baos, book.get());
                        byte[] response = baos.toByteArray();
                        OutputStream responseBody = httpExchange.getResponseBody();
                        responseBody.write(response);
                        responseBody.close();
                    } else {
                        httpExchange.sendResponseHeaders(OK, -1);
                    }
                } else {
                    httpExchange.sendResponseHeaders(NOT_ALLOWED, -1);
                }
            });

            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}