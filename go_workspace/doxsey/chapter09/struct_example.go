package main

import "fmt"

type Person struct{
  firstName string
  lastName string
  age int
}

func main(){
  me := new(Person)
  me.firstName = "Greg"
  me.lastName = "Jay"
  me.age = 33
  fmt.Println(*me)
  //fmt.Println(me.firstName, me.lastName, me.age)
  me.printOut()
}

func (p *Person) printOut(){
  fmt.Println(p.firstName, p.lastName , p.age)
}
