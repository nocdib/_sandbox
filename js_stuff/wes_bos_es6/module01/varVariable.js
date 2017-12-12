var outer = 10;

// Re-declaration of the "outer" variable
function setOuter(){
	var outer = 20;
	console.log(`outer = ${outer}`);
}

// Function to add two number and return the sum
function add(x,y){
	return x+y;
}

// Takes a function as a parameter then runs that function with two parameters
function takesFunc(f){
	var a = 1;
	var b = 2;
	console.log(f(a,b));
}

console.log(outer); // 10
setOuter(); // 20
let a = 1;
console.log(`a = ${a}`) // a = 1
takesFunc(add) // 3
