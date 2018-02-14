package conditionals.challenge

import java.util.*

fun main(args: Array<String>) {
    val random = Random().nextInt(50) + 1

    when (random) {
        in 1..10 -> println("$random is between 1 and 10")
        in 11..20 -> println("$random is between 11 and 10")
        in 21..30 -> println("$random is between 21 and 10")
        in 31..40 -> println("$random is between 31 and 10")
        else -> println("$random is over 40")
    }
}