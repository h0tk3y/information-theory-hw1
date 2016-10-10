import java.io.File

fun kGrams(input: String, k: Int): List<String> = (0..input.length - k).map { input.substring(it, it + k) }
fun String.escapeNewLine() = replace("\n", "⏎")

fun <T> empiricProbabilities(items: List<T>): Map<T, Double> =
        items.groupBy { it }.mapValues { it.value.size.toDouble() / items.size }

fun entropyByProbabilities(probabilities: Iterable<Double>) =
        probabilities.map { -1 * it * Math.log(it) / Math.log(2.0) }.sum()

fun main(args: Array<String>) {
    val input = File(args[0]).readText()
    val n = args[1].toInt()

    val nGrams = kGrams(input, n)
    val probabilities = empiricProbabilities(nGrams)

    println("--- Empiric probabilities: ---\n")

    val probabilitiesDescending = probabilities.entries.sortedByDescending { it.value }
    for ((nGram, p) in probabilitiesDescending) {
        println("${nGram.escapeNewLine()}\t- $p")
    }

    println("\n--- H_n(x) ---\n")

    val entropyN = entropyByProbabilities(probabilities.values)
    val entropyNByLetter = entropyN / n

    println("H_n(X) = $entropyN / $n = $entropyNByLetter")
}