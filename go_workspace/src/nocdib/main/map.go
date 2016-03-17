package main

import (
	"fmt"
)

func main() {

  var myMap map[int]int
  myMap = make(map[int]int)
	myMap[1] = 2

  a,b := myMap[3]
  fmt.Println(a, b)
	fmt.Println(myMap)

	nameMap := map[int]string{
		0:"Greg",
		1:"Gary",
		2:"Gizelle",
	}
	fmt.Println(nameMap)

	for key,value := range nameMap{
		fmt.Println(key, " - ", value)
	}
}
