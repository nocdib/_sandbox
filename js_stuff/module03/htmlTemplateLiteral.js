const kids = [
{name:'Greg', age:35},
{name:'Gary', age:34},
{name:'Gizelle', age:30}
];

const markup = `
<ul>
	${kids.map((kid,x,y,z) => `<li>${kid.name} is ${kid.age} years old. <b>${x}, ${y[x].name}</b></li>`).join('\n\t')}
</ul>
`;
// kid - The individual array object
// x - The index of the array object
// y - the entire array
// z - undefined
console.log(markup)
