package com.example.asus.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    RealmConfiguration realmConfig;

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.textView);

        realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(realmConfig);
        realm = Realm.getInstance(realmConfig);
        Init();
        query1();//find users who have "Brown" color dog
        query2();
        query3();
        update1();
        update2();
        delete1();
        int i=0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    void Init()
    {
        realm.beginTransaction();
        Dog dog1 = realm.createObject(Dog.class);
        Dog dog2 = realm.createObject(Dog.class);
        Dog dog3 = realm.createObject(Dog.class);
        Dog dog4 = realm.createObject(Dog.class);

        dog1.setId("A");dog1.setName("Fido");dog1.setColor("Brown");
        dog2.setId("B");dog2.setName("Fluffy");dog2.setColor("Red");
        dog3.setId("C");dog3.setName("Fluffy");dog3.setColor("Brown");
        dog4.setId("D");dog4.setName("Fluffy");dog4.setColor("Yellow");

        User user1 = realm.createObject(User.class);
        User user2 = realm.createObject(User.class);

        user1.setId("U1");user1.setName("Jane");
        user1.getDogs().add(dog1);
        user1.getDogs().add(dog2);

        user2.setId("U2");user2.setName("John");
        user2.getDogs().add(dog2);
        user2.getDogs().add(dog3);
        user2.getDogs().add(dog4);
        realm.commitTransaction();
        tv.append("Dog list:\n");
        PrintDogs(realm.where(Dog.class).findAll());
        tv.append("\nUser list:\n");
        PrintUsers(realm.where(User.class).findAll());
    }
    void query1(){
        RealmResults<User> users = realm.where(User.class)
                .equalTo("dogs.color", "Brown")
                .findAll();
        tv.append("\n\nFetch: Find all users who have at least 1 brown dog\n");
        PrintUsers(users);
    }
    void query2(){
        RealmResults<User> r1 = realm.where(User.class)
                .equalTo("dogs.name","Fluffy")
                .findAll();
        RealmResults<User> r2 = r1.where()
                .equalTo("dogs.color", "Brown")
                .findAll();
        tv.append("\n\nFetch: Find all users who have at least 1 dog named Fluffy and have at least 1 brown dog\n");
        PrintUsers(r2);
    }
    void query3(){
        RealmResults<User> r1 = realm.where(User.class)
                .equalTo("dogs.name", "Fluffy")
                .equalTo("dogs.color", "Brown")
                .findAll();
        RealmResults<User> r2 = realm.where(User.class)
                .equalTo("dogs.name", "Fluffy")
                .findAll()
                .where()
                .equalTo("dogs.color", "Brown")
                .findAll()
                .where()
                .equalTo("dogs.color", "Yellow")
                .findAll();

        tv.append("\n\nFetch: Find all users who have at least 1 dog named Fluffy and have at least 1 brown dog and have at least 1 yellow dog\n");
        PrintUsers(r2);
    }
    void update1(){//change user name = "Alex" whose ID is U1
        User rr = realm.where(User.class).equalTo("id","U1").findFirst();
        realm.beginTransaction();
        rr.setName("Alex");
        realm.commitTransaction();
        tv.append("\n\nUpdate: Change user name = 'Alex' whose ID is U1");
        tv.append("\nAll users: \n");
        PrintUsers(realm.where(User.class).findAll());
    }
    void update2(){//add new dog to U1
        User rr = realm.where(User.class).equalTo("id","U1").findFirst();

        realm.beginTransaction();
        Dog dog = realm.createObject(Dog.class);
        dog.setId("E");
        dog.setName("Tyler");
        dog.setColor("Black");
        rr.getDogs().add(dog);
        realm.commitTransaction();
        tv.append("\n\nUpdate: Add new dog to U1");
        tv.append("\nAll dogs:\n");
        PrintDogs(realm.where(Dog.class).findAll());
        tv.append("\nAll users:\n");
        PrintUsers(realm.where(User.class).findAll());
    }
    void delete1(){
        User rr = realm.where(User.class).equalTo("id","U1").findFirst();
        realm.beginTransaction();
        rr.deleteFromRealm();
        realm.commitTransaction();
        tv.append("\n\nDelete: Delete user U1");
        tv.append("\nAll dogs:\n");
        PrintDogs(realm.where(Dog.class).findAll());
        tv.append("\nAll users:\n");
        PrintUsers(realm.where(User.class).findAll());
    }

    void PrintDogs(RealmResults<Dog> a){
        for(int i=0;i<a.size();i++) {
            tv.append("id: " + a.get(i).getId() + " | name: " + a.get(i).getName() + " | color: " + a.get(i).getColor() + "\n");
        }
    }
    void PrintUsers(RealmResults<User> a){
        String user ="";
        for(int i=0;i<a.size();i++) {
            user += "id: " + a.get(i).getId() + " | name: " + a.get(i).getName() + " | dogs: ";
            for (int j = 0; j < a.get(i).getDogs().size(); j++) {
                user += a.get(i).getDogs().get(j).getId() + " ";
            }
            user+="\n";
        }
        tv.append(user + "\n");
    }

}

