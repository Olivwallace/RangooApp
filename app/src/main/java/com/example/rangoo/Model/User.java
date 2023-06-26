package com.example.rangoo.Model;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {
    private String name;
    private String phone;
    private String address;
    private String birthday;

    @Exclude
    private LoginData loginData;

    public User(String name, String phone, String address, String birthday, String email, String password){
        setName(name);
        setPhone(phone);
        setAddress(address);
        setBirthday(birthday);
        setLoginData(new LoginData(email, password));
    }

    // ---------------- Setter's and Getter's

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLoginData(LoginData loginData){
        this.loginData = loginData;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public LoginData getLoginData() { return  loginData; }

    // ---------------- Utils
    public String toJSON(){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}
