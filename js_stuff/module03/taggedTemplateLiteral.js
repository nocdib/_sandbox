const manipulator = function(string, ...values){
	retval = '';
	return values;


};

let name = 'Greg';
let values = ['one','two','three'];
const str = manipulator`This is ${name} the string ${values}`;

//console.log(str);

const rest = function(...args){
	return args.map(arg => arg).join('\n');
};

console.log(str);
//console.log(rest('Greg', 'Gary', 'Gizelle'));
