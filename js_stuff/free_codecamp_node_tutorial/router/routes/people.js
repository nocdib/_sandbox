const express = require('express');
const route = express.Router();
const mw = require('../middleware/mw');

// This middleware only runs for /people routes
route.get('*', [mw.peopleMiddleware]);

// {base url}/people
route.get('/', (req, res) => {
    res.send(req.message + 'Hit the /people route');
});

// {base url}/people/example
route.get('/example', (req, res) => {
    res.send(req.message + 'Hit the /people/example route');
});

module.exports = route;



