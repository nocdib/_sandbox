package com.nocdib;

public class Poodle extends Dog{

    public Poodle(String name){
        super(name, "Poodle");
    }

    public String getName() {
        return this.name;
    }

    public String getBreed() {
        return this.breed;
    }
}
