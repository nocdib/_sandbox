/*

1) Create a class, Book, with a title and an author.

2) Add a method, readPage(), that increases the value of a private variable, currentPage, by 1.

3) Create a subclass of Book; name it eBook.

4) eBook also takes in a format, which defaults to "text".

5) In eBooks, counting words makes more sense than pages. Override the readPage() method to increase the word count by
    250, the average number of words per page from typewriter days.

*/

open class Book(open var title: String = "", open var author: String = "") {

    var currentPage: Int = 0

    open fun readPage() {
        currentPage++
    }

    override fun toString(): String {
        return "[${title} - ${author}]"
    }

}

class eBook(override var title: String = "", override var author: String = "", var format: String = "text"): Book(title, author) {

    var wordsRead: Int = 0

    override fun readPage() {
        wordsRead + 250
    }

    override fun toString(): String {
        return "[${title} - ${author} - ${format}]"
    }

}

fun main() {

    var mobyDick = Book(title = "Moby Dick", author = "Herman Melville")
    var farenheit451 = eBook(title = "Farenheit 451", author = "Kurt Vonnegut")

    println(mobyDick)
    println("-----------------")
    println(farenheit451)

}

