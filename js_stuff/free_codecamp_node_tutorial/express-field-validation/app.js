const express = require('express');
const path = require('path');
const Joi = require('joi');
const bodyParser = require('body-parser');

const app = express();

// Middleware to map the actual static/ to the symbolic public/
app.use('/public', express.static(path.join(__dirname, 'static')));

// Parse url-encoded form
app.use(bodyParser.urlencoded({extended: false}));

// Serve the index page when the user goes to http://localhost:3000/
app.get('/', (req, res) => {
	res.sendFile(path.join(__dirname, 'index.html'));
});

/* When the user clicks the form's Submit button validate the email and
 password fields then display both values
 */
app.post('/', (req, res) => {
	const schema = Joi.object().keys({
		email: Joi.string().trim().email().required(), // email must be a string in the proper format and is mandatory
		password: Joi.string().min(6).max(15), // password is a mandatory string of at least 6 and at most 15 characters
	});

	// Next, validate the user data against the schema
	Joi.validate(req.body, schema, (err, result) => {
		if(err){
			res.send(`Error occured: ${err}`);
		} else {
			res.send(`EMAIL: ${req.body.email} <br> PASSWORD: ${req.body.password}`);
		}
	});
});

app.listen(3000);
