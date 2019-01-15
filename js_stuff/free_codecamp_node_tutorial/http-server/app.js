const http = require('http');
const server = http.createServer((req,res) => {
	res.write('Hello World from Node.');
	res.end();
});

server.listen(3000);