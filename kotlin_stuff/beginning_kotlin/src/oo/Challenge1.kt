package oo

interface Lendable{
    fun borrowItem()
    fun returnItem()
}

abstract class InventoryItem(): Lendable{
    abstract val title: String
    abstract val genre: String
    abstract val publicationYear: Int
    abstract var borrowed: Boolean

    override fun borrowItem(){
        if(borrowed){
            println("This item is already being borrowed.")
        }else{
            println("You have borrowed: $title")
            borrowed = true
        }
    }

    override fun returnItem(){
        if(borrowed){
            println("Item returned: $title")
            borrowed = false
        }else{
            println("You cannot return an item that was not borrowed.")
        }
    }

}

data class BookItem(override val title: String,
                    val author: String,
                    override val genre: String,
                    override val publicationYear: Int,
                    override var borrowed: Boolean = false): InventoryItem(){


}

data class DVDItem(override val title: String,
                   val length: Int,
                   override val genre: String,
                   override val publicationYear: Int,
                   override var borrowed: Boolean = false): InventoryItem(){

}

fun main(args: Array<String>) {

    val warAndPeace = BookItem("War and Peace","Leo Tolstoy","Historical Fiction",1867)
    println(warAndPeace)
    warAndPeace.borrowItem()
    warAndPeace.returnItem()

    val allEyezOnMe = DVDItem("All Eyez On Me",7940,"Rap",1996,true)
    println(allEyezOnMe)
    allEyezOnMe.borrowItem()
    allEyezOnMe.returnItem()
    allEyezOnMe.returnItem()



}