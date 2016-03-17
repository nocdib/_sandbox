package main

import "fmt"

func main() {
   var val int = 4

  switch  {
  case val == 3:
      fmt.Println("3")
    case val > 0:
      fmt.Println("2")
      fallthrough
    case 1 > 2:
      fmt.Println("1")
    default:
      fmt.Println("NONE")
    }

}
