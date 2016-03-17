package main

import (
	"encoding/json"
	"fmt"
)

type Example struct {
	Word      string
	Number    int
	invisible int
}

func main() {

	var ex1, ex2 Example
	ex1 = Example{"Greg", 34, 5}

	ex1_bytes, _ := json.Marshal(ex1)
	fmt.Println(string(ex1_bytes))


	json.Unmarshal(ex1_bytes, &ex2)
	fmt.Println(ex2.Word, ex2.Number, ex2.invisible)

}
