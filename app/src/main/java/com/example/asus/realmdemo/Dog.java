package com.example.asus.realmdemo;

/**
 * Created by ASUS on 5/19/2016.
 */
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ASUS on 4/9/2016.
 */
public class Dog extends RealmObject {
    private String id;
    private String name;
    private String color;

    public Dog(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Dog() {

    }
    //constructors and getters,setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

