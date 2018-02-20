package conditionals

fun main(args: Array<String>) {

    // If Statement

    val age: Int = 0

    if(age > 20) {
        println("${age} is legal.")
    } else {
        println("${age} is not legal.")
    }
    println()

    // When Statement

    when(age){
        0 -> println("Age is ${age}")
        in 1..10 -> println("${age} is between 1 and 10")
        in 11..20 -> println("${age} is between 11 and 20")
        else -> println("${age} is less than 1 or over 20")
    }
    println()

    // Conditional Expression

    val language: String = "spanish"
    val greeting: String =
            if(language.equals("spanish")) {
                "Hola"
            } else {
                "Hello"
            }
    println(greeting)

}