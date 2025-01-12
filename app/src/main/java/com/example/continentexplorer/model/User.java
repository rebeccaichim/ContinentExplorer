package com.example.continentexplorer.model;

public class User {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String gender;
    private int age;
    private String profileImageUrl;


    // Constructor gol (necesar pentru Retrofit sau alte biblioteci)
    public User() {}

    // Constructor cu parametrii
    public User(Long id, String fullName, String email, String gender, int age) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}