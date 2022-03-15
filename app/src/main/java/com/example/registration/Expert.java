package com.example.registration;

public class Expert {

    String name,password,email;

    Expert(String name, String password, String email){
        this.name=name;
        this.password=password;
        this.email=email;
    }

    Expert(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
