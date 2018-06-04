/*
 * Primary file for API
 *
 */

// Dependencies
const http = require('http');
const url = require('url');
const { StringDecoder } = require('string_decoder');
const handler = require('./01_basic_server_handler');
const router = require('./01_basic_server_router');

// Configure the server to respond to all requests with a string
const server = http.createServer(function (req, res) {
	let parsedUrl = url.parse(req.url,true);
	console.log(parsedUrl);
	let payload = '';

	if(req.method == 'POST') {
		let decoder = new StringDecoder('utf-8');

		req.on('data', function (data) {
			payload = payload.concat(decoder.write(data));
		});
	
		req.on('end', function () {
			payload = payload.concat(decoder.end());
			console.log('Request Received\n----------');
			console.log(payload);
		});
	}

	let data = {
		path: parsedUrl.pathname.trim(),
		queryObject: parsedUrl.query,
		method: req.method,
		headers: req.headers,
		payload: payload || {},
	}

	const respObj = router.routes(data);

	res.end(respObj);
	
});

// Start the server
server.listen(3000, function () {
	console.log('The server is up and running now');
});