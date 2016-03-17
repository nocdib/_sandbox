/*
Write a function which takes an integer. The function will have two returns. The first return should be the argument divided by 2.
The second return should be a bool that let’s us know whether or not the argument was even. For example:
half(1) returns (0, false)
half(2) returns (1, true)
*/
package main

import "fmt"

func main() {
	half := func(x int) (y int, even bool) {
		y = x / 2
		even = x%2 == 0
		return
	}

	fmt.Println(half(1))
	fmt.Println(half(2))
	fmt.Println(half(3))
	fmt.Println(half(4))
}
