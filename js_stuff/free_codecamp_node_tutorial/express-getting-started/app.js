const express = require('express');
const app = express();

// First argument in the route, second is the callback
app.get('/hello/:name', (req,res)=>{
	res.send(`Well, hello ${req.params.name}`);
})

/* http://localhost:3000/example/greg?name=joshua
		"name: greg" will appear in req.params
		"name: joshua" will appear in req.query
*/
app.get('/example/:name', (req, res) => {
	console.log(req.params);
	console.log(req.query);
	res.send(`Reached the example route: ${req.params.name}, ${req.query.name}`);
})

app.listen(3000);