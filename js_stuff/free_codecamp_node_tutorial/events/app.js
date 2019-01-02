const events = require('events');

// Person objects can emit and listen to events
class Person extends events.EventEmitter {
    constructor(name){
        super();
        // Set the name
        this._name = name;
        // add the emitter to output 'My name is..' on each name event
        this.on('name', () => console.log(`My name is ${this.name}`));
    }
    // getter method for name variable
    get name() { return this._name; }
}

let greg = new Person("Greg");
greg.emit('name');
let elise = new Person("Elise");
elise.emit('name');
let emerie = new Person("Emerie");
emerie.emit('name');