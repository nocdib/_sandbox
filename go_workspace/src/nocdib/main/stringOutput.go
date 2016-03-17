package main

import "fmt"

func main() {
  rune := 'f'
  multi := `
  This
  is
  a
  multi-line string
  `
  multiTicks := `
  This is a multi-line string
  with
  ` + "`backtics` "

  fmt.Println(rune)
  fmt.Println(multi)
  fmt.Println(multiTicks)
}
