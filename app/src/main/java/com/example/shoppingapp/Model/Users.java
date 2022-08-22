package com.example.shoppingapp.Model;

public class Users {
    private String phone;

    public Users( String phone, String name, String password ) {
        this.phone = phone;
        this.name = name;
        this.password = password;
    }

    private String name;

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    private String password;

    public Users(){};






}
