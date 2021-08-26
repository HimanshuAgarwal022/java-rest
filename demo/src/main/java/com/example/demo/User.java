package com.example.demo;

import java.util.Objects;

public class User {

    private String Id;
    private String name;

    public User() {
    }

    public User(String Id, String name) {
        this.Id = Id;
        this.name = name;
    }


    public User Id(String Id) {
        setId(Id);
        return this;
    }

    public User name(String name) {
        setName(name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(Id, user.Id) && Objects.equals(name, user.name);
    }

    @Override
    public String toString() {
        return "{" +
            " Id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
