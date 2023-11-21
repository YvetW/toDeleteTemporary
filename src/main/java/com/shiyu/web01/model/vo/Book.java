package com.shiyu.web01.model.vo;

public class Book {
    private Integer id;
    private String bookname;
    private String author;
    private String publisher;

    public Book() {
    }

    public Book(Integer id, String bookname, String author, String publisher) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
