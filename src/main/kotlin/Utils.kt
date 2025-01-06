import java.math.BigInteger
import java.security.MessageDigest
import java.time.LocalDate
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, year: Int = LocalDate.now().year) = Path("src/main/resources/$year/$name.txt").readLines()

/**
 * Converts string to .md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun List<List<Number>>.print() {
    forEach { numbers -> numbers.joinToString("").println() }
}