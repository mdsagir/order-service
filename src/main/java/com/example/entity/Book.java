package com.example.entity;

import lombok.Data;

@Data
public class Book {

    private String isbn;
    private String title;
    private String author;
    private Double price;
}
