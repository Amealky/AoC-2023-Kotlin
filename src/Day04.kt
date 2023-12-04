
fun main() {

    fun extractNumbers(numbers: String) : List<String> {
        val regex = Regex("[0-9]+")
        return regex.findAll(numbers).map { it.value }.toList()
    }

    fun cardSolver(line: String, winCallback: () -> Unit) {
        val cardNumbers = line.split(":")[1]
        val (winningNumbers, myNumbers) = cardNumbers.split("|").map { extractNumbers(it) }

        myNumbers.forEach {myNumber ->
            if(winningNumbers.contains(myNumber)) {
                winCallback()
            }
        }
    }

    fun part1(input: List<String>): Int {
        var totalCardsValue = 0
        input.forEach { line ->
            var cardValue = 0
            cardSolver(line) { //Each winning number
                cardValue = if(cardValue == 0) 1 else cardValue*2
            }
            totalCardsValue += cardValue
        }
        return totalCardsValue
    }

    fun part2(input: List<String>): Int {
        var scratchedCardsTotal = 0
        val multiplicators = input.associateWith { 1 }.toMutableMap()
        input.forEachIndexed { index, line ->
            var numberOfCardsToDuplicate = 0
            cardSolver(line) { //Each winning number
                numberOfCardsToDuplicate++
            }

            for(i in 1..numberOfCardsToDuplicate) {
                val nextIndex = index+i
                if(nextIndex < input.size) {
                    multiplicators[input[nextIndex]] = multiplicators[input[nextIndex]]!! + multiplicators[line]!!
                }
            }

            scratchedCardsTotal += multiplicators[line]!!
        }
        return scratchedCardsTotal
    }

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    //check(part1(testInput) == 13)
    //check(part2(testInput) == 30)

    print("[TEST] DAY 04 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 04 Result : part 2 -> ")
    part2(testInput).println()

    print("DAY 04 Result : part 1 -> ")
    part1(input).println()

    print("DAY 04 Result : part 2 -> ")
    part2(input).println()
}