const num = 7;
let display = '';
let result = 1;

for(x = num; x > 0; x --){
	result = result * x;
	display += `${x} ${x == 1 ? '' : '* '}`;
}
if(num == 0) {
	console.log('0! = 1')
} else {;
	console.log(`${num}! = ${display}= ${result}`);
}