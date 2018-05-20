package oo

import kotlin.test.assertTrue

class Book(val title: String, val author: String, val pubYear: Int, var borrowed: Boolean = false){

    var number: Int = 0
        get() = field + 7
        set(value){
            field = value * 2
        }

    fun borrow(): Boolean {
        if(isBorrowed()){
            return true
        }
        return false
    }

    fun returnBook(): Boolean {
        if(isBorrowed()){
            borrowed = false
            return true
        }
        return false
    }

    fun isBorrowed() = borrowed

    fun print(){
        println("${title}\n${author}\n${pubYear}\nBorrowed: ${borrowed}\n")
    }

}

fun main(args: Array<String>) {
    val ugr = Book("The Underground Railroad", "Colson Whitehead", 2016)
    val nm = Book("Norse Mythology", "Neil Gaiman", 2016, true)
    val cp = Book(author = "Alice Walker", borrowed = false, title = "The Color Purple", pubYear = 2016)
    println(cp.number)
    cp.number = 13
    println(cp.number)
    cp.print()
    ugr.print()
    nm.print()
}