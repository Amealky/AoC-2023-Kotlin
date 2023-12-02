fun main() {

    fun digitsSolver(sentence: String) : Int {
        val digits = sentence.filter { it.isDigit() }
        return "${digits.first()}${digits.last()}".toInt()
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach {sentence ->
            result += digitsSolver(sentence)
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val numberDictionnary = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        input.forEach {sentence ->
            val sentenceToDigitalize = sentence.toCharArray()
            numberDictionnary.forEachIndexed { index, s ->
                //Only need to get the digit of the first and last number words
                val numberIndexFirst = sentence.indexOf(s)
                val numberIndexLast = sentence.lastIndexOf(s)

                //We do index+1 cause the digit value is equal to the index position + 1 ( ex : One index = 0 so 0+1 = 1 one )
                if(numberIndexFirst != -1) sentenceToDigitalize[numberIndexFirst] = (index+1).digitToChar()
                if(numberIndexLast != -1) sentenceToDigitalize[numberIndexLast] = (index+1).digitToChar()
            }
            result += digitsSolver(String(sentenceToDigitalize))
        }
        return result
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    //check(part1(testInput) == 142)
    check(part2(testInput) == 281)
    //print("DAY 01 TEST Result : part 1 -> ")
    //part1(testInput).println()

    print("DAY 01 TEST Result : part 2 -> ")
    part2(testInput).println()

    val input = readInput("Day01")
    print("DAY 01 Result : part 1 -> ")
    part1(input).println()

    print("DAY 01 Result : part 2 -> ")
    part2(input).println()
}
