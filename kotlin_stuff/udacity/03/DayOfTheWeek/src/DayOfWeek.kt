import java.util.*

fun main(args: Array<String>){
    println("What day is it today?")
    println("Today is ${dayOfWeek()}")
}

fun dayOfWeek(): String {
    return when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        else -> "Saturday"
    }
}
