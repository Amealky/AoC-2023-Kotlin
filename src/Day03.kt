fun main() {


    fun charIsSymbol(char: Char) : Boolean {
        val symbolRegex = Regex("[^0-9.]")
        return symbolRegex.matches(char.toString())
    }

    //Return false by default ( we assume that the number is NOT valid per default )
    fun checkTop(charIndex: Int, lineIndex: Int, input: List<String>) : Boolean{
        input.getOrNull(lineIndex-1)?.let {topLine ->
            return charIsSymbol(topLine[charIndex])
        }
        return false
    }

    fun checkBottom(charIndex: Int, lineIndex: Int, input: List<String>) : Boolean {
        input.getOrNull(lineIndex+1)?.let {bottomLine ->
            return charIsSymbol(bottomLine[charIndex])
        }
        return false
    }

    fun checkLeft(charIndex: Int, line:String) : Boolean {
        line.getOrNull(charIndex-1)?.let { previousChar ->
            return charIsSymbol(previousChar)
        }
        return false
    }

    fun checkRight(charIndex: Int, line:String) : Boolean {
        line.getOrNull(charIndex+1)?.let { nextChar ->
            return charIsSymbol(nextChar)
        }
        return false
    }

    fun checkAngle(charIndex: Int, lineIndex: Int, input: List<String>) : Boolean {
        var angleContainSymbol = false
        //Top Left
        input.getOrNull(lineIndex-1)?.let { previousLine ->
            previousLine.getOrNull(charIndex-1)?.let { TopLeftChar ->
                angleContainSymbol = charIsSymbol(TopLeftChar)
            }
        }

        //Top Right
        input.getOrNull(lineIndex-1)?.let { previousLine ->
            previousLine.getOrNull(charIndex+1)?.let { TopRightChar ->
                if(!angleContainSymbol) angleContainSymbol = charIsSymbol(TopRightChar)
            }
        }

        //Bottom Left
        input.getOrNull(lineIndex+1)?.let { nextLine ->
            nextLine.getOrNull(charIndex-1)?.let { BottomLeftChar ->
                if(!angleContainSymbol) angleContainSymbol = charIsSymbol(BottomLeftChar)
            }
        }

        //BottomRight
        input.getOrNull(lineIndex+1)?.let { nextLine ->
            nextLine.getOrNull(charIndex+1)?.let { BottomRightChar ->
                if(!angleContainSymbol) angleContainSymbol = charIsSymbol(BottomRightChar)
            }
        }
        return angleContainSymbol
    }

    /*fun extractNumbers(engineLine: String, callback: (number : String, first_digit_index: Int) -> Unit) {
        val regex = Regex("([0-9]+)")
        regex.findAll(engineLine).forEach {
            callback(it.groupValues[1], it.range.first)
        }
    }*/

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEachIndexed { lineIndex, line ->
            var numberIsValid = false
            var numberBuffer = ""
            for(charIndex in line.indices) {
                //if is digit keep track of the number and check surrounding position
                if(line[charIndex].isDigit()) {
                    numberBuffer += line[charIndex]

                    if(!numberIsValid) numberIsValid = checkTop(charIndex, lineIndex, input)
                    if(!numberIsValid) numberIsValid = checkBottom(charIndex, lineIndex, input)
                    if(!numberIsValid) numberIsValid = checkLeft(charIndex, line)
                    if(!numberIsValid) numberIsValid = checkRight(charIndex, line)
                    if(!numberIsValid) numberIsValid = checkAngle(charIndex, lineIndex, input)
                    continue
                }

                //print all wrong number and surrounding lines
                /*if(!numberIsValid && numberBuffer.isNotEmpty()) {
                    println("WRONG NUMBER")
                    println(numberBuffer)
                    println(input.getOrNull(lineIndex-1))
                    println(line)
                    println(input.getOrNull(lineIndex+1))
                }*/

                //if not digit check if number buffered is valid if not just keep going
                if(numberIsValid) {
                    result += numberBuffer.toInt()
                    numberIsValid = false
                }

                numberBuffer = ""

            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day03_test")
    //check(part1(testInput) == 4361)
    //check(part2(testInput) == 2286)

    println("[TEST] DAY 03 Result : part 1 -> ")
    //part1(testInput).println()

    println("[TEST] DAY 03 Result : part 2 -> ")
    //part2(testInput).println()

    val input = readInput("Day03")
    println("DAY 03 Result : part 1 -> ")
    part1(input).println()

    println("DAY 03 Result : part 2 -> ")
    //part2(input).println()
}