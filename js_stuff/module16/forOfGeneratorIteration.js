function* list() {
  yield('Line 1');
  yield('Line 2');
  yield('Line 3');
  yield('Line 4');
  yield('Line 5');
}

console.log(list().next().value); // Line 1
// Line 1
console.log('--------------');


for(const line of list()){
  console.log(line);
}
/* Line 1
   Line 2
   Line 3
   Line 4
   Line 5 */
