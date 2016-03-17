package main

import (
	"fmt"
)

const dat = "death and taxes"

func main() {
	fmt.Println("Nothing is certain except " + dat)
	//bit shifting
	fmt.Printf("1 << 1 = %d\n", 1<<1)
	fmt.Printf("1 << 1 = %b\n", 1<<1)
	fmt.Printf("1 << 2 = %d\n", 1<<2)
	fmt.Printf("1 << 2 = %b\n", 1<<2)
	fmt.Printf("1 << 3 = %d\n", 1<<3)
	fmt.Printf("1 << 3 = %b\n", 1<<3)
}
