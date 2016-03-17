package main

import (
	"fmt"
)

func main() {
	var text string = "Greg"
	fmt.Printf("The variable text is a %T with a value of \"%s\" and an address of %#x\n", text, text, &text)
	var textAddress *string = &text
	fmt.Printf("The address of text is %#x and its value is %s\n", textAddress, *textAddress)
	newAddress(textAddress)
}

func newAddress(x *string) {
	fmt.Printf("In newAddress() the address of text is %#x and its value is %s\n", x, *x)
}
