package oo

class Person {
    var name: String = "Sarah"
    var age: Int = 42

    fun speak(){
        println("${this.name} says \"hello\"")
    }

    fun getBirthYear(): Int{
        return 2018 - this.age
    }

}

fun main(args: Array<String>) {
    val person = Person()
    println("${person.name} is ${person.age} years old")
    println("${person.name} was born in ${person.getBirthYear()}")
    person.age = 36
    person.name = "Greg"
    println("${person.name} is ${person.age} years old")
    person.speak()
    println("${person.name} was born in ${person.getBirthYear()}")
}