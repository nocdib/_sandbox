/*
What's the value of this expression:
(true && false) || (false && true) || !(false && false)?
*/
package main

import "fmt"

func main() {
	fmt.Println((true && false) || (false && true) || !(false && false))
}
