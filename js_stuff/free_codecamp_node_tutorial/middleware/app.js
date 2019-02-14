const express = require('express');
const path = require('path');

const bodyParser = require('body-parser');

const app = express();
let message = "";
// Middleware to map the actual static/ to the symbolic public/
app.use('/public', express.static(path.join(__dirname, 'static')));

// Parse url-encoded form
app.use(bodyParser.urlencoded({extended: false}));

// This middleware runs for every route
app.use((req,res,next) => {
	message += 'This middleware runs for every route';
	next();
});

// This middleware only runs for /example
app.use('/example', (req,res,next) => {
	message += '<br>This middleware only runs from /example';
	next();
});


// Display the message no matter which route is navigated to
app.get('*', (req, res) => {
	res.send(message);
	message = "";
});

app.listen(3000);
