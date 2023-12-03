fun main() {

    fun charIsSymbol(char: Char, onlyGear: Boolean = false) : Boolean {
        if(onlyGear) {
            return char == '*'
        }
        val symbolRegex =  Regex("[^0-9.]")
        return symbolRegex.matches(char.toString())
    }

    //Return false by default ( we assume that the number is NOT valid per default )
    fun checkTop(charIndex: Int, lineIndex: Int, input: List<String>, onlyGear: Boolean = false) : List<Int>?{
        input.getOrNull(lineIndex-1)?.let {topLine ->
            if(charIsSymbol(topLine[charIndex], onlyGear = onlyGear)) return listOf(lineIndex-1, charIndex)
        }
        return null
    }

    fun checkBottom(charIndex: Int, lineIndex: Int, input: List<String>, onlyGear: Boolean = false) : List<Int>? {
        input.getOrNull(lineIndex+1)?.let {bottomLine ->
            if(charIsSymbol(bottomLine[charIndex], onlyGear = onlyGear)) return listOf(lineIndex+1, charIndex)
        }
        return null
    }

    fun checkLeft(charIndex: Int, lineIndex:Int, input: List<String>, onlyGear: Boolean = false) : List<Int>? {
        input[lineIndex].getOrNull(charIndex-1)?.let { previousChar ->
            if(charIsSymbol(previousChar, onlyGear = onlyGear)) return listOf(lineIndex, charIndex-1)
        }
        return null
    }

    fun checkRight(charIndex: Int, lineIndex:Int, input: List<String>, onlyGear: Boolean = false) : List<Int>? {
        input[lineIndex].getOrNull(charIndex+1)?.let { nextChar ->
            if(charIsSymbol(nextChar, onlyGear = onlyGear)) return listOf(lineIndex, charIndex+1)
        }
        return null
    }

    fun checkTopLeftAngle(charIndex: Int, lineIndex: Int, input: List<String>, onlyGear: Boolean = false): List<Int>? {
        input.getOrNull(lineIndex-1)?.let { previousLine ->
            previousLine.getOrNull(charIndex-1)?.let { TopLeftChar ->
                if(charIsSymbol(TopLeftChar, onlyGear = onlyGear)) return listOf(lineIndex-1, charIndex-1)
            }
        }
        return null
    }

    fun checkTopRightAngle(charIndex: Int, lineIndex: Int, input: List<String>, onlyGear: Boolean = false): List<Int>? {
        //Top Right
        input.getOrNull(lineIndex-1)?.let { previousLine ->
            previousLine.getOrNull(charIndex+1)?.let { TopRightChar ->
                if(charIsSymbol(TopRightChar, onlyGear = onlyGear)) return listOf(lineIndex-1, charIndex+1)
            }
        }
        return null
    }

    fun checkBottomLeftAngle(charIndex: Int, lineIndex: Int, input: List<String>, onlyGear: Boolean = false): List<Int>? {
        input.getOrNull(lineIndex+1)?.let { nextLine ->
            nextLine.getOrNull(charIndex-1)?.let { BottomLeftChar ->
                if(charIsSymbol(BottomLeftChar, onlyGear = onlyGear)) return listOf(lineIndex+1, charIndex-1)
            }
        }
        return null
    }


    fun checkBottomRightAngle(charIndex: Int, lineIndex: Int, input: List<String>, onlyGear: Boolean = false): List<Int>? {
        input.getOrNull(lineIndex+1)?.let { nextLine ->
            nextLine.getOrNull(charIndex+1)?.let { BottomRightChar ->
                if(charIsSymbol(BottomRightChar, onlyGear = onlyGear)) return listOf(lineIndex+1, charIndex+1)
            }
        }
        return null
    }

    /*fun extractNumbers(engineLine: String, callback: (number : String, first_digit_index: Int) -> Unit) {
        val regex = Regex("([0-9]+)")
        regex.findAll(engineLine).forEach {
            callback(it.groupValues[1], it.range.first)
        }
    }*/


    //Can be cleaned by merge with part2 or totally separate them
    fun part1(input: List<String>): Int {
        var result = 0
        var numberBuffer = ""
        var numberIsValid = false
        input.forEachIndexed { lineIndex, line ->
            for(charIndex in line.indices) {
                //if is digit keep track of the number and check surrounding position
                if(line[charIndex].isDigit()) {
                    numberBuffer += line[charIndex]
                    if(!numberIsValid) numberIsValid = checkTop(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkBottom(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkLeft(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkRight(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkTopLeftAngle(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkBottomLeftAngle(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkBottomRightAngle(charIndex, lineIndex, input) != null
                    if(!numberIsValid) numberIsValid = checkTopRightAngle(charIndex, lineIndex, input) != null
                    continue
                }


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

    fun recordsGearCoordsAndNumbers(gearCoords: List<Int>, number: Int, gearsNumberRecords:HashMap<List<Int>, ArrayList<Int>>) : HashMap<List<Int>, ArrayList<Int>> {
        //If its first time we add those coord init the hashmap entry with empty array
        if(!gearsNumberRecords.containsKey(gearCoords)) {
            gearsNumberRecords[gearCoords] = arrayListOf()
        }
        gearsNumberRecords[gearCoords]?.add(number)
        return gearsNumberRecords
    }

    fun resolveTotalGearRatio(gearsNumberRecords:HashMap<List<Int>, ArrayList<Int>>) : Int {
        var totalRatio = 0
        gearsNumberRecords.values.forEach {
            val cleanedNumbers = it.toSet().toList()
            if(cleanedNumbers.size == 2) {
                val gearRatio = cleanedNumbers.reduce { acc, i -> acc*i }
                totalRatio += gearRatio
            }
        }
        return totalRatio
    }

    fun part2(input: List<String>): Int {
        var result = 0
        var numberBuffer = ""

        var gearsCoordsWithAssociatedNumbers = HashMap<List<Int>, ArrayList<Int>>()

        var gearCoordsForADigit = ArrayList<List<Int>>()

        input.forEachIndexed { lineIndex, line ->
            for(charIndex in line.indices) {
                if(line[charIndex].isDigit()) {
                    numberBuffer += line[charIndex]
                    checkTop(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it) }
                    checkBottom(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    checkLeft(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    checkRight(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    checkTopLeftAngle(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    checkBottomLeftAngle(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    checkBottomRightAngle(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    checkTopRightAngle(charIndex, lineIndex, input, true)?.let { gearCoordsForADigit.add(it)}
                    continue
                }

                //if not digit check if number buffered is valid if not just keep going
                if(gearCoordsForADigit.isNotEmpty()) {
                    //we loop on every gear coords registered for each digit of this number

                   gearCoordsForADigit.forEach { coords ->
                       gearsCoordsWithAssociatedNumbers = recordsGearCoordsAndNumbers(coords, numberBuffer.toInt(), gearsCoordsWithAssociatedNumbers)
                    }
                    gearCoordsForADigit.clear()
                }

                numberBuffer = ""
            }
        }

        return resolveTotalGearRatio(gearsCoordsWithAssociatedNumbers)

    }

    val testInput = readInput("Day03_test")
    //check(part1(testInput) == 4361)
    //check(part2(testInput) == 467835)

    println("[TEST] DAY 03 Result : part 1 -> ")
    part1(testInput).println()

    println("[TEST] DAY 03 Result : part 2 -> ")
    part2(testInput).println()

    val input = readInput("Day03")
    println("DAY 03 Result : part 1 -> ")
    part1(input).println()

    println("DAY 03 Result : part 2 -> ")
    part2(input).println()
}