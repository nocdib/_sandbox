function* letters(value) {
  yield(`In letter() A-${value}`);
  yield(`In letter() B-${value}`);
}

// Generator function that increments a number by 1 ten times
function* numbers(num) {
  console.log(`Starting number is ${num}`);
  for(let count = 1; count < 11; count++){
    yield(`The number is now ${num + count}`);
    yield* letters(num+count);
  }
  // yield* letters(num);
}

// Retrieves the iterator object from the numbers generator
const genr8 = numbers(10);
// While the iterator has values, print them
let item = genr8.next();
while(!item.done) {
  console.log(item.value);
  item = genr8.next();
}
