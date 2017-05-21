let names = ["Leonardo", "Donatello", "Michaelangelo", "Raphael"];
let species = "Turtle";

let obj = names.map((name, rank) => ({name: name, rank: ++rank, species}));
console.log(obj);

let percentages = [10,20,30,40,50,60,70,80,90,100];
let greaterThanHalf = percentages.filter((percentage) => (percentage > 50));
console.log(greaterThanHalf);
