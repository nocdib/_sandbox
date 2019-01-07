const fs = require('fs');
const inputFilename = '1mb.txt';
const outputFilename = 'output.tmp';

// Get the size of the input file
const fileSize = fs.statSync(inputFilename).size;
console.log(`\n${inputFilename} is ${fileSize} bytes.`);

// If a previous temp file exists then delete it
if(fs.existsSync(outputFilename)) {
	fs.unlinkSync(outputFilename);
	console.log(`Successfully deleted ${outputFilename}`);
}

// Open a read stream from the 1mb text file
const readStream = fs.createReadStream(inputFilename, {
	encoding: 'utf-8',
	flags: 'r',
	autoClose: true
});

// Write the contents of the text file to a new file
const writeStream = fs.createWriteStream(outputFilename, 'utf-8');
let lineCount = 1;
readStream.on('data', (chunk) => {
	//writeStream.write(`(${lineCount})\n${chunk}\n`);
	writeStream.write(chunk);
	lineCount++;
});

/* 
When the stream is finished reading, output the number of bytes that were read and written.
*/
readStream.on('end', () => {
	console.log(`The stream read ${readStream.bytesRead} bytes.`);
	writeStream.end();
});

writeStream.on('finish', () => {
	console.log(`The stream wrote ${writeStream.bytesWritten} bytes.\n`);
});