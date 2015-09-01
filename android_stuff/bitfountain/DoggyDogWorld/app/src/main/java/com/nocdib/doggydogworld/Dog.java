package com.nocdib.doggydogworld;


public class Dog {

    private String  name,
            breed;
    private int age;

    public Dog(){
        name = "Fido";
        breed = "Mutt";
        age = 2;
    }

    public Dog(String name, String breed, int age){
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public String getBreed(){
        return breed;
    }

    public int getAge(){
        return age;
    }

    public String bark(){
        return "Woof! Woof!";
    }

}
