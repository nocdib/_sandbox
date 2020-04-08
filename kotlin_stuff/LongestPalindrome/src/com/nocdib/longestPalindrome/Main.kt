/*  Challenge inspired by Java Brains' video: https://www.youtube.com/watch?v=DK5OKKbF6GI   */
package com.nocdib.longestPalindrome

// Variable to count the number of times a word is tested for a palindrome
var comparisons: Int = 0

fun main() {
    println( longestPalindrome(""))
    println("--------------------------")
    println(longestPalindrome("naomi, did I moan?"))
    println("--------------------------")
    println(longestPalindrome("Gregory"))
    println("--------------------------")
    println(longestPalindrome("racecarizard"))
}

/* Return the longest palindrome in a string, excluding single-characters */
fun longestPalindrome(str: String = ""): String {
    // Remove all non-alphanumeric characters from the string
    var alphanumericString = str.replace(("[^A-Za-z0-9]").toRegex(), "")
    println(alphanumericString)
    var longestPalindrome: String = ""
    comparisons = 0

    // The first letter in the substring
    for(x in 0..alphanumericString.length) {
        // The last letter in the substring
        for(y in alphanumericString.length downTo x) {
            // If the first letter comes before the last letter and the size of the substring is greater than the current longest palindrome then start comparing.
            if(x < y && (y-x) > longestPalindrome.length) {
                // Test if the substring from the first to last letters is a palindrome...
                var testString: String = alphanumericString.substring(x,y)
                // ...and if it is then see if it's longer than the current longest palindrome
                if(isPalindrome(testString)) {
                    longestPalindrome = if (longestPalindrome?.length < testString.length)  testString else longestPalindrome
                    return "The longest palindrome in \"$str\" is \"$longestPalindrome\" and it took $comparisons comparisons to find."
                }
            }
        } // Decrease the index of the last letter
    } // Increase the index of the first letter
    return "The longest palindrome in \"$str\" is \"$longestPalindrome\" and it took $comparisons comparisons to find."
}

/* Return true if a string reads the same forwards and backwards */
fun isPalindrome(str: String) : Boolean {
    comparisons++
    //println("isPalindrome: ${str}")
    return str.toLowerCase().equals(str.toLowerCase().reversed()) && str.length > 1
}