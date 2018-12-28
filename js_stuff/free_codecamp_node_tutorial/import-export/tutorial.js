/*
Exporting a module encapsulated by an object can have its members destructured
on import so that they can be called without reference to the parent object.

e.g.
const {
	sum,
	plusOne,
	universalNumber
} = require('./tutorial');
const {
	sum,
	plusOne,
	universalNumber
} = require('./tutorial');

*/
const utils = {
	sum: (num1,num2) => num1 + num2,
	plusOne: (num) => num + 1,
	universalNumber: 42
};

module.exports = utils;
