
const http = require('http');

/* 
Create an HTTP server on port 3000. On request, write a simple string message as a response.
*/
const server = http.createServer((req,res) => {
	res.write('Hello World from Node.');
	res.end();
});

server.listen(3000);