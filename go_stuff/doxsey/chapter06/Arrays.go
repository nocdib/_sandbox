package main


import "fmt"

func main(){

	var arrOne [5]string
	arrOne[0] = "first"
	arrOne[1] = "second"
	arrOne[2] = "third"
	arrOne[3] = "fourth"
	arrOne[4] = "fifth"

	fmt.Println(arrOne)

	for _, value := range arrOne{
		fmt.Println(value)
	}

	arrTwo := [5]{1,2,3,4,5}

	for _, value := range arrTwo{
		fmt.Println(value)
	}
}

