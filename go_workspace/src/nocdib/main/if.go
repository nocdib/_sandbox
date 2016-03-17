package main

import "fmt"

func main() {
	var a, b int
	b = -2
	if a = 1; b > 3 {
		fmt.Println("You can go with this.")
	} else if b = 0; b < 2 {
		fmt.Println("You can go with that.")
	}
	fmt.Printf("a = %v and b = %v\n", a, b)
}
