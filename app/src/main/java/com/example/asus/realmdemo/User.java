package com.example.asus.realmdemo;

/**
 * Created by ASUS on 5/19/2016.
 */
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by ASUS on 4/9/2016.
 */
public class User extends RealmObject{
    private String id;
    private String name;
    private RealmList<Dog> dogs;
    //constructors and getters, setters
    public User() {
    }

    public User(String id, String name, RealmList<Dog> dogs) {

        this.id = id;
        this.name = name;
        this.dogs = dogs;
    }


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

    public RealmList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(RealmList<Dog> dogs) {
        this.dogs = dogs;
    }
    public void addDog(Dog dog)
    {
        dogs.add(dog);
    }
}

