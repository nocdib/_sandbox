package main

import "fmt"

func main() {
  mySlice := []int{4,5,6}
  fmt.Println(mySlice)
  fmt.Printf("length = %d, capacity = %d\n", len(mySlice), cap(mySlice))
  mySlice = append(mySlice, 1)
  mySlice = append(mySlice, 2)
  mySlice = append(mySlice, 3)
  mySlice = append(mySlice, 4)
  fmt.Println(mySlice)
  fmt.Printf("length = %d, capacity = %d\n", len(mySlice), cap(mySlice))

  mySlice = make([]int,2,3)
  mySlice[0] = 1
  mySlice[1] = 2
  mySlice = append(mySlice, 3, 4, 5, 6, 7)
  fmt.Println(mySlice)
  fmt.Printf("length = %d, capacity = %d\n", len(mySlice), cap(mySlice))
  var x []int
  x = new([10]int)[0:5]
  fmt.Printf("length = %d, capacity = %d\n", len(x), cap(x))

  oneSlice := []int{1,2,3}
  twoSlice := []int{4,5,6}
  fmt.Println(append(oneSlice,twoSlice...))
}
