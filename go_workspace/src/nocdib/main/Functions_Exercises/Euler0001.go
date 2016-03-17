/*
If we list all the natural numbers below 10 that are multiples of 3 or 5,
we get 3, 5, 6 and 9. The sum of these multiples is 23.

Find the sum of all the multiples of 3 or 5 below 1000.
*/

package main

import "fmt"

func main() {
	var sum int
	// For every number from 3 to 999 check its divisibility by 3 and 5
	for x := 3; x < 1000; x++ {
		// If it is divisible by either number then add it to the sum
		if x%3 == 0 || x%5 == 0 {
			sum += x
		}
	}
	fmt.Println(sum)
}
