package io

import kotlin.math.max

fun main(args: Array<String>) {

    val word = getNewWord().toLowerCase() // The target word
    val wordChars = word.toSet() // The unique letters in the target word
    var maskedWord = "_".repeat(word.length).toMutableList() // The word where unguessed letters are dashes
    var numGuesses = 0 // Number of guesses made
    var numCorrectGuesses = 0 // Number of correct guesses made
    val maxGuesses = 8 // Maximum number of guesses allowed
    var guessList = mutableSetOf<Char>() // List of characters that were guessed
    var correctGuess: Boolean // Whether the character that is guessed is in the word

    // Accept guesses until that maximum is reached
    gameloop@ while(numGuesses < maxGuesses){
        correctGuess = false // assume that the guess is incorrect until proven otherwise
        println("You have ${maxGuesses-numGuesses} guesses remaining")
        print("Enter a letter: ")
        var guess = readLine()
        if(guess != null){
            var guessChar = guess.toCharArray()[0]
            numGuesses++ // increase the guess count
            // Look at each character in the word
            for(index in 0 until word.length) {
                // if the guess matches a character in the word then update the masked word
                if (word[index] == guessChar) {
                    // if the guessed letter has not been guessed before then...
                    if (!guessList.contains(guessChar)) {
                        guessList.add(guessChar) // add the character to the guessed list
                        numCorrectGuesses++ // increate the number of correct guesses
                        correctGuess = true // indicate that the character is a correct guess.
                    }
                    maskedWord.set(index, guessChar)
                }
            }

            if(!correctGuess){
                guessList.add(guessChar)
            }

            printMaskedWord(maskedWord)
            println("Guesses: $guessList\n")


            if(numCorrectGuesses == wordChars.size){
                println("You Win!")
                break@gameloop
            }
        }
    }

    if(numGuesses == maxGuesses && numCorrectGuesses < wordChars.size){
        println("You Lose! The answer is \"$word\"")
    }
}

fun getNewWord(): String {
    return "banana"
}

fun printMaskedWord(maskedWord: MutableList<Char>){
    for(letter in maskedWord) {
        print("$letter ")
    }
    println()
}