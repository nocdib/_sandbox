package oo

data class Person2(val firstName: String, val lastName: String, val age: Int){

}

fun main(args: Array<String>) {
    val greg = Person2("Gregory", "Joshua", 36)
    val gary = Person2("Gary", "Joshua", 35)
    val gary2 = gary.copy(firstName = "Christopher")
    println(greg)
    println(gary2)
    val (xyz) = greg
    println("$xyz")
}