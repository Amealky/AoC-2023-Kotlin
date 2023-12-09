import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

fun readInputString(name:String) = Path("src/$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


fun extractNumbers(numbers: String) : List<String> {
    val regex = Regex("-?[0-9]+")
    return regex.findAll(numbers).map { it.value }.toList()
}

fun stringIsNumber(str: String) : Boolean {
    val symbolRegex =  Regex("[0-9]")
    return symbolRegex.matches(str)
}


/*** Extensions ***/

fun String.onlyContains(char: Char) : Boolean {
    return this.all { it == char }
}

fun List<String>.onlyContains(value: String) : Boolean {
    return this.all { it == value }
}

fun List<String>.onlyFinishBySame(char: Char) : Boolean {
    return this.all{ it.endsWith(char)}
}

fun <T> List<T>.onlyContains(value: T) : Boolean {
    return this.all { it == value }
}