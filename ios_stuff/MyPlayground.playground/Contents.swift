var fibMemo: [UInt: UInt] = [0: 0, 1: 1]  // our old base cases
func fib3(n: UInt) -> UInt {
    if let result = fibMemo[n] {  // our new base case
        return result
    } else {
        fibMemo[n] = fib3(n: n - 1) + fib3(n: n - 2)  // memoization
    }
    return fibMemo[n]!
}

print(fib3(n: 50))
print("Hello")
