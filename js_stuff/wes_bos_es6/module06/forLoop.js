const arr = ["one", "two", "three", "four"];

for(const element in arr){
  console.log(element, arr[element]);
}
console.log('=================');
const addUpNumbers = function(...numbers){
  let total = 0;
  for(num of numbers){
    total += num;
  }
  console.log(numbers, total);
  //console.log(arguments);
};

addUpNumbers(1,2,3,4,5);
