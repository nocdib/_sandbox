package main

import "fmt"

func main(){
  var temp string

  temp = "Initial value"
  fmt.Println("The value is: " + temp)
  changeValue(&temp)
  fmt.Println("The value is: " + temp)
  var ( x = 1
        y = 2
      )
  swap(&x, &y)
  fmt.Println(x)
  fmt.Println(y)
}

func changeValue(value *string){
  *value = "second value"
  //text := "example"
  //text_pointer := &text
  //fmt.Println(*text_pointer)
}

func swap(a,b *int){
  temp := new(int)
  *temp = *a
  *a = *b
  *b = *temp
}
