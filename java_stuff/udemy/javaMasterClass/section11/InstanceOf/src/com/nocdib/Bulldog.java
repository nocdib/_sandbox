package com.nocdib;

public class Bulldog extends Dog{

    public Bulldog(String name){
        super(name, "Bulldog");
    }

    public String getName() {
        return this.name;
    }

    public String getBreed() {
        return this.breed;
    }
}
