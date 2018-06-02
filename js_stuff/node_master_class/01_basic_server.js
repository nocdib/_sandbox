/*
 * Primary file for API
 *
 */

// Dependencies
const http = require('http');
const url = require('url');

// Configure the server to respond to all requests with a string
const server = http.createServer(function (req, res) {
	let parsedUrl = url.parse(req.url,true);
	console.log(parsedUrl);
	res.end('Response Sent');
});

// Start the server
server.listen(3000, function () {
	console.log('The server is up and running now');
});