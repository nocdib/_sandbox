const fs = require('fs');
const inputFilename = '../streams/1mb.txt';
const outputFilename = 'output.tmp';

// If a previous temp file exists then delete it
if (fs.existsSync(outputFilename)) {
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
readStream.pipe(writeStream);

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