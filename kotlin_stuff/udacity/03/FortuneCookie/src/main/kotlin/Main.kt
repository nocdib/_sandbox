// Call an endpoint the returns a random fortune and present it to the user

fun main(args: Array<String>) {
    var cookie = FortuneCookie("http://yerkee.com/api/fortune")

    println("The fortune endpoint is: ${cookie.getEndpoint()}")

    println("-------------\nYour Fortune\n-------------\n${cookie.getFortune()}")
}