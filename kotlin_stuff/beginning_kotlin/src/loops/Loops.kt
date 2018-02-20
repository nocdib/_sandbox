package loops

fun main(args: Array<String>) {

    // prints the letters a-d paired with the numbers 1-5 but breaks when the number is above 3
    alpha@ for(a in 'a'..'d') {
        num@ for(b in 1..5){
            if(b > 3){
                continue@alpha
            }
            println("${a} - ${b}")
        }
    }

}