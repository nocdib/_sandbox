package main

import (
	"fmt"
)

func main() {
	x := "x"
	y := "y"
	z := "z"
	{
		y := "b"
		fmt.Printf("%s, %s, %s\n", x, y, z)
	}
	fmt.Printf("%s, %s, %s\n", x, y, z)

}
