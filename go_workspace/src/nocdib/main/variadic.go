package main

import "fmt"

func main() {
  /*greet("greg")
  greet("greg", "gary")
  greet("greg", "gary", "gizelle")*/
  names := []string{"greg", "gary", "gizelle"}
  greet(names...)
}

func greet(names ...string){
  fmt.Print("Hello, there: ")
  for _, name := range names {
    fmt.Print(name + ", ")
  }
  fmt.Println()
}
