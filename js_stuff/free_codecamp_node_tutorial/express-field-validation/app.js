const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');

const app = express();

// Middleware to map the actual static/ to the symbolic public/
app.use('/public', express.static(path.join(__dirname, 'static')));

// Parse url-encoded form
app.use(bodyParser.urlencoded({extended: false}));

// Parse JSON
app.use(bodyParser.json());

// Serve the index page when the user goes to http://localhost:3000/
app.get('/', (req, res) => {
	res.sendFile(path.join(__dirname, 'index.html'));
});

/* When the user clicks the form's Submit button print the body of the request
	to the console then tell the user that the data was sent.
 */
app.post('/', (req, res) => {
	console.log(req);
	// Do whatever backend work here
	res.json({success: 'Successfully posted data'});
});

app.listen(3000);
