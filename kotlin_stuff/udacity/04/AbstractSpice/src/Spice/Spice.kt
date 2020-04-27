package Spice/*

Let's go back to your spices. Make Spice an abstract class, and then create some subclasses that are actual spices.

1) It's easiest (organizationally) if you make a new package, Spices, with a file, Spice, that has a main() function.

2) Copy/paste your Spice class code into that new file.

3) Make Spice abstract.

4) Create a subclass, Curry. Curry can have varying levels of spiciness, so we don't want to use the default value, but
    rather pass in the spiciness value.

5) Spices are processed in different ways before they can be used. Add an abstract method prepareSpice to Spice, and
    implement it in Curry.

6) Curry is ground into a powder, so let's call a method grind(). However, grinding is something that's not unique to
    curry, or even to spices, and it's always done in a grinder. So we can create an Interface, Grinder, that implements
    the grind() method. Do that now. Then add the Grinder interface to the Curry class.

 */

abstract class Spice(var name: String, open var spiciness: String = "none" ) {

    var heat: Int = 0
        get() {
            return when(spiciness) {
                "mild" -> 5
                "medium" -> 7
                "hot" -> 10
                else -> 0
            }
        }

    init {
        println(this)
    }

    abstract fun prepareSpice()

    override fun toString(): String {
        return "[${name} - ${spiciness} - ${heat}]"
    }
}

class Curry(override var spiciness: String): Spice("Curry", spiciness), Grinder {

    override fun grind() {
        println("* grinding *")
    }

    override fun prepareSpice() {
        grind()
    }
}

fun main(args: Array<String>) {
    println("valid")
}

interface Grinder {
    fun grind()
}