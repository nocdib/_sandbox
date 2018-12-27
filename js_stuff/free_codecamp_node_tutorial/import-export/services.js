/* Encapsulating the functions and variables in a single object allows it to 
be exported into a variable and called in reference to that variable.

e.g.

const services = require('./services'); // import this file
services.printOne(); // prints "One"
services.printTwo(); // prints "Two"
*/

module.exports = {
	printOne() {
		console.log('One');
	},

	printTwo() {
		console.log('Two');
	},
};
