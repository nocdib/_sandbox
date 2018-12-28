const events = require('events');

class Person extends events.EventEmitter {
    constructor(name){
        super();
        // Set the name
        this._name = name;
        // add the emitter
        this.on('name', () => console.log(`My name is ${this.name}`));
    }

    get name() { return this._name; }
}

let greg = new Person("Greg");
greg.emit('name');
let elise = new Person("Elise");
elise.emit('name');
let emerie = new Person("Emerie");
emerie.emit('name');