const greg = Symbol('Greg');
console.log(greg);

const joshuas = {
  [Symbol('greg')]: 35,
  [Symbol('gary')]: 34,
  [Symbol('gizelle')]: 30
}
console.log(joshuas[Symbol('greg')]);

const keys = Object.getOwnPropertySymbols(joshuas);
const vals = keys.map(key => joshuas[key]);
console.log(vals);
