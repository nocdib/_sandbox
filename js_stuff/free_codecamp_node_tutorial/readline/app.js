// The readline module provides an interface for reading data from a Readable
// stream (such as process.stdin) one line at a time.
const readline = require('readline');

// Read/write to/from standard input/output
const readlineObj = readline.createInterface({
	input: process.stdin,
	output: process.stdout
});

// Set the prompt then display it
readlineObj.setPrompt('What is the answer to life?\n');
readlineObj.prompt();
// Read user input
readlineObj.on('line', (line) => {
	switch (line.trim()) {
		// If the user enters '42'...
		case '42':
			console.log('You got it!');
			// ...close the i/o object.
			readlineObj.close();
			break;
		default:
			// If the user enters anything else then prompt input again.
			console.log(`'${line.trim()}' is incorrect. Try again.\n`);
			readlineObj.prompt();
			break;
	}
})
// When i/o is closed bid the user farewell.
readlineObj.on('close', () => console.log('Have a great day!'));