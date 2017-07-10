const words = ["make","this", "into", "a", "sentence"];

const temp = words.reduce(function(sentence, word){return sentence += word}
  ,"ZZ");

 console.log(temp);

/*



The reduce function is used to distill the values of a collection into a
single value, like an array of numbers into a single sum. It takes as input
a function and an initial value.

Collection.reduce(function(x,y){
...
}, initialValue);

x is the accumulator
y is the iteration value
initialValue is the starting value of the accumulator


*/
