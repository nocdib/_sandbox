function* entry() {
  let count = 0;
  yield* increment(count);
}

function* increment(count) {
  console.log('This');
  console.log('That');
  yield(`The current count is ${count}`);
  count++;
  yield(`The current count is ${count}`);
}

const temp = entry();
console.log(temp.next().value);
console.log(temp.next().value);
