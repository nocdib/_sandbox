package com.nocdib;

public class Dog {

    protected String name;
    protected String breed;

    public Dog(String name){
        this(name, "Mutt");
    }

    public Dog(String name, String breed){
        this.name = name;
        this.breed = breed;
    }

    public String getName() {
        return this.name;
    }

    public String getBreed() {
        return this.breed;
    }
}
