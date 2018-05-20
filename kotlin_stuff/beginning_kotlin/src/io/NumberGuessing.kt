package io

import java.util.*

fun main(args: Array<String>) {

    val number = Random().nextInt(100) + 1
    var input: String?
    var guess = 0

    while(guess != number) {
        print("Enter a number between 1 and 100: ")
        input = readLine()
        if(input != null) {
            try {
                guess = input.toInt()
                if(guess < number){
                    println("$guess is too low")
                }else if(guess > number) {
                    println("$guess is too high")
                }else{
                    println("$guess is the number")
                }
            }
            catch(e: NumberFormatException){
                println("\"$input\" is not a number")
            }
        }
    }
    println("Game Over!")
}