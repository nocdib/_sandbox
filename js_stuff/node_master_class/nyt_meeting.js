let x = 10
function outer() {
	let counter = 0;
	function incrementCounter(){
		return counter + x ;
	}
	return incrementCounter;
}

let temp = outer();
console.log(temp());
x = 11;
console.log(temp());