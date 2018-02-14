package conditionals

/**
 * Created by cyclops on 1/15/18.
 */
fun main(args: Array<String>) {
    val x: Int = 4
    when(x){
        in 1..3 -> { println("less than 3")}
        in 4..7 -> { println("less than 7")}
        in 8..10 -> { println("less than 10")}
    }

    if(x in 1..10) {
        println("** x is between 1 and 10 **")
    }
}
