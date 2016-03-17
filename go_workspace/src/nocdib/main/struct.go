package main

import "fmt"

func main() {

  type person struct{
    firstName string
    middleName string
    lastName string
    age int
  }

  p1 := person{"Gregory","C", "Joshua",34}
  fmt.Println(p1.firstName, p1.middleName, p1.lastName, p1.age)
}
