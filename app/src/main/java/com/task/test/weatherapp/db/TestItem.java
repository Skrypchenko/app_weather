package com.task.test.weatherapp.db;

/**
 * Created by Yevgen on 15.06.2017.
 */

public class TestItem {

    public long id;
    public String name = "Steve";
    public String surname = "Jobs";
    public String email = "Save@abc.com";
    public String contact = "67890";

    public TestItem(String name, String surname, String email, String contact) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.contact = contact;
    }

    public TestItem(long id, String name, String surname, String email, String contact) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.contact = contact;
    }
}
