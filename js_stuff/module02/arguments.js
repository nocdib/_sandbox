
const getArgs = function(){
  args = Array.from(arguments)
  console.log(`There are ${args.length} arguments`);
   arr = args.map((val, idx) => ({val,idx}))
   console.log(arr);
};

getArgs(1,2,3);
