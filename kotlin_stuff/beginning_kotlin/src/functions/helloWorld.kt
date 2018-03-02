package functions

fun helloWorld(param: String, num: Int): String{
    return("Hello, $param - $num")
}

fun main(args: Array<String>) {
    println(helloWorld("Greg", 5))
}