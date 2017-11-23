class Dog {

    constructor(name, breed){
        this.name = name;
        this.breed = breed;
    }

    bark(){
        console.log(`Bark! My name is ${this.name}`);
    }
}

const renley = new Dog('Renly', 'Beagle');

console.log(renley);
renley.bark();