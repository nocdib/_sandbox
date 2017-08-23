let first = ["Royler", "Relson", "Rickson"];
let second = ["Renzo", "Ralph", "Ryan"];

console.log(first);
console.log(second);
first.push(...second);
console.log(first);

const obj = {
  first,
  second
}

console.log("second =",obj.second);
