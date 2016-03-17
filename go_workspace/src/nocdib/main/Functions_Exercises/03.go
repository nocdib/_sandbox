/*
Write a function with one variadic parameter that finds the greatest number
in a list of numbers.
*/
package main

import "fmt"

func greatest(list ...int) (max int) {
	max = list[0]
	for x := range list {
		if list[x] > max {
			max = list[x]
		}
	}
	return
}

func main() {
	fmt.Println(greatest([]int{1, 2, 10, 4, 5}...))
}
