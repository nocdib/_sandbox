const key = '455281a3506945bb9fd203206172707';
const url = `http://api.apixu.com/v1/current.json?key=${key}&q=Paris`;
console.log(url);
const weatherPromise = fetch(url);
let myData = null;

let zig = weatherPromise
  .then(payload => payload.json()).then(json => { myData = json; }).catch(Error('Something went wrong'))

function doStuffWithData(data) {
  console.log(data.current);
}

function checkData(callback) {
  if (myData) {
    callback(myData)
    return;
  }
  setTimeout(() => { checkData(callback)}, 100);
}
checkData(doStuffWithData);

console.log(`zig = ${zig}`);
