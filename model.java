package com.example.myapplication1;

public class model {
    private String userId;
    private String name;
    private String age;
    private String phone;

    // Empty constructor required for Firebase
    public model() {
    }

    public model(String userId, String name, String age, String phone) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}