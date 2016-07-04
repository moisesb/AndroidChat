package com.borges.moises.chatinenglish.data.model;

import java.io.Serializable;

/**
 * Created by Moisés on 03/07/2016.
 */

public class Contact implements Serializable{
    private String name;
    private String userName;
    private int age;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
