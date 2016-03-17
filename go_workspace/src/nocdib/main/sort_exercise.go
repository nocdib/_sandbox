package main

import(
  "fmt"
  "sort"
)
type people []string

func (p people) Len() int           { return len(p) }
func (p people) Less(i, j int) bool { return p[i] < p[j] }
func (p people) Swap(i, j int)      { p[i],p[j] = p[j],p[i] }

func main() {
  studyGroup := people{"Zeno","John","Al","Jenny"}
  s:= []string{"Zeno","John","Al","Jenny"}
  n:=[]int{7,4,8,2,9,19,12,32,3}

  fmt.Println("\nstudyGroup\n------------")
  fmt.Println(studyGroup, " -- unsorted")
  sort.Sort(studyGroup)
  fmt.Println(studyGroup, " -- ascsending")
  sort.Sort(sort.Reverse(studyGroup))
  fmt.Println(studyGroup, " -- descending")

  fmt.Println("\ns\n---")
  fmt.Println(s, " -- unsorted")
  fmt.Printf("%T\n",s)
  t :=sort.StringSlice(s)
  sort.Sort(t)
  fmt.Println(t, " -- ascending")
  sort.Sort(sort.Reverse(t))
  fmt.Println(t, " -- descending")

  fmt.Println("\nn\n---")
  fmt.Println(n, " -- unsorted")
  sort.Ints(n)
  fmt.Println(n, " -- ascending")
  sort.Sort(sort.Reverse(sort.IntSlice(n)))
  fmt.Println(n, " -- descending")
  //fmt.Printf("%v is of type %T\n",n,n)

}
