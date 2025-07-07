package com.example.myapplication1;

public class Book {
    private String id;
    private String imageUrl;
    private String title;
    private String description;
    private String actualPrice;
    private String sellingPrice;

    public Book() {
        // Required empty constructor for Firebase
    }

    public Book(String id, String imageUrl, String title, String description, String actualPrice, String sellingPrice) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.actualPrice = actualPrice;
        this.sellingPrice = sellingPrice;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }
}
