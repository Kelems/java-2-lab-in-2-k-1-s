package org.example.library.domain;

public class Book {
    private Integer id;
    private String title; // Название книги
    private String author; // Автор / Авторы
    private String location; //местоположение книги
    private boolean issued; // Арендуется ли книга?
    private boolean readingRoom; // Выдается ли только в читальном зале?


// --------------------- Конструктор ---------------------
    public Book(Integer id,String title, String author, String location,boolean issued, boolean readingRoom) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.location = location;
        this.issued = issued;
        this.readingRoom = readingRoom;
    }


// --------------------- Геттеры ---------------------
    public Integer getId() {
    return id;
}
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getLocation() {
        return location;
    }
    public boolean getIssued() {
        return issued;
    }
    public boolean getReadingRoom() {
        return readingRoom;
    }


// --------------------- Сеттеры ---------------------
    public void setId(Integer id) {
    this.id = id;
}
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setIssued(boolean issued) {
        this.issued = issued;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setReadingRoom(boolean readingRoom) {
        this.readingRoom = readingRoom;
    }
}