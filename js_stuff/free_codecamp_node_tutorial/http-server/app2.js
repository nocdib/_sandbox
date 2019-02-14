
const http = require('http');
const fs = require('fs');

/* 
Create an HTTP server on port 3000. On request, serve a file.
*/
const server = http.createServer((req,res) => {
	// Send an OK status, set the header to a jpeg content-type
	res.writeHead(200, {'Content-type':'image/jpeg'});
	// Read the jpg file into a stream and send it to the response object.
	// The response object is also a type of output stream
	fs.createReadStream('./fodacy.jpg').pipe(res);
});

server.listen(3000);