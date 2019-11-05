function sumOfMultiplesOf(multiple, upperBound){
    upperBound--;
    upperBound = Math.floor(upperBound / multiple);
    return multiple * ((upperBound * (upperBound+1)) / 2);
}

n=1000000000
console.log(sumOfMultiplesOf(3,n) + sumOfMultiplesOf(5,n) - sumOfMultiplesOf(15,n));