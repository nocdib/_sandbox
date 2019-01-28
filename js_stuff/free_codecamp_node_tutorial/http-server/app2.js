
const http = require('http');
const fs = require('fs');

/* 
Create an HTTP server on port 3000. On request, serve a file.
*/
const server = http.createServer((req,res) => {
	res.writeHead(200, {'Content-type':'image/jpeg'});
	fs.createReadStream('./fodacy.jpg').pipe(res);
});

server.listen(3000);