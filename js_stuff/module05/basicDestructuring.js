person = {
  firstname: "Jon",
  lastname: "Smith",
  age: 57,
  children: {
    sons: ['Cameron', 'Jason'],
    daughters: ['Abigail']
  }
};

console.log("--- USING OBJECT PROPERTIES DIRECTLY ---")
console.log(person.firstname);
console.log(person.lastname);
console.log(person.age);
console.log(person.children.sons);
console.log(person.children.daughters);
console.log("--- DESTRUCTURING OBJECT ---")
let {firstname} = person;
console.log(`firstname is ${firstname}`);
let {lastname,age} = person;
console.log(`age is ${age} and lastname is ${lastname}`)
console.log("--- DESTRUCTURING OBJECT INTO DIFFERENT VARIABLE NAMES ---")
let {firstname:a, lastname:b, age:c, children: kids} = person;
console.log(`a is ${a}, b is ${b}, and c is ${c}`);
console.log(`kids are ${kids}`);
console.log(`sons are ${kids.sons}`);
let {daughters} = person.children;
console.log(`daughters are ${daughters}`);

// Destructuring can have default values for undefined properties
var {wife} = person;
console.log(`wife variable is undefined: ${wife}`);
var {wife="none"} = person;
console.log(`wife variable is has a value of: ${wife}`);
