fun main(args: Array<String>){
    when(Integer.parseInt(args[0])) {
        in 0..12 -> println("Good morning, Kotlin.")
        in 12..23 -> println("Good night, Kotlin.")
        else -> println("Invalid hour.")
    }
}