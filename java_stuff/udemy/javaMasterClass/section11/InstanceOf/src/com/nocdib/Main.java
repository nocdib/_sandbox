package com.nocdib;

public class Main {

    public static void main(String[] args) {
        Dog dog = new Dog("Rover");
        Poodle fifi = new Poodle("Fifi");
        Bulldog barry = new Bulldog("Barry");
        dog = barry;

        System.out.println(dog.getBreed());
        System.out.println(fifi.getBreed());
        System.out.println(barry.getBreed());

        if(dog instanceof Dog){
            System.out.println("dog is a Dog");
        }
        if(fifi instanceof Dog){
            System.out.println("fifi is a Dog");
        }
        if(barry instanceof Dog){
            System.out.println("barry is a Dog");
        }

        if(dog instanceof Poodle){
            System.out.println("dog is a Poodle");
        }
        if(fifi instanceof Poodle){
            System.out.println("fifi is a Poodle");
        }
        if( barry.getClass().equals(Poodle.class)){
            System.out.println("barry is a Poodle");
        }


    }
}
