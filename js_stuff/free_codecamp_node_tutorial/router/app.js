const express = require('express');
const path = require('path');
const people = require('./routes/people');
const app = express();
const mw = require('./middleware/mw');



// This middlware applies a base message for every route
app.get('*', mw.baseMiddleware);

// This middleware applies a message only to /example routes
app.get('/example', mw.baseExampleMiddleware);

// Use the "people" router when navigating to /people routes
app.use('/people', people);

// If it is not a /people route then send the message from here
app.get('*', (req, res) => {
	res.send(req.message);
});





app.listen(3000);
