fun main() {

    fun getLeftRightSequence(input: List<String>) : String { return input[0]}

    fun getMap(input: List<String>) : HashMap<String, List<String>> {
        val mapLines = input.subList(2, input.size)
        val map = HashMap<String, List<String>>()
        mapLines.forEach { line ->
            val lineDatas = line.split(" = ", "(", ")", ",", " ").filter { it.isNotEmpty() }
            map[lineDatas[0]] = listOf(lineDatas[1], lineDatas[2])
        }
        return map
    }

    fun move(actualPos:String, map: HashMap<String, List<String>>, direction: Char) : String{
        var nextPos = actualPos
        when(direction) {
            'L' -> nextPos = map[actualPos]!!.first()
            'R' -> nextPos = map[actualPos]!!.last()
        }
        return nextPos
    }


    fun countMove(startPos:String, map: HashMap<String, List<String>>, leftRightSequence: String, whileCondition: (String) -> Boolean) : Long {
        var actualPos = startPos
        var numberOfSteps = 0L
        while (whileCondition(actualPos)) {
            for (direction in leftRightSequence) {
                actualPos = move(actualPos, map, direction)
                numberOfSteps++
                //No need to move the next if reached
                if (actualPos.endsWith('Z')) return numberOfSteps
            }
        }

        return  numberOfSteps
    }

    fun greatestCommonDivisor(a: Long, b: Long): Long {
        return if (b == 0L) a else greatestCommonDivisor(b, a % b)
    }

    fun leastCommonMultiple(a: Long, b: Long): Long {
        return a * b / greatestCommonDivisor(a, b)
    }

    fun getStartPositions(positions: List<String>, imAGhost : Boolean = false) : List<String> {
        return if(imAGhost) positions.filter { it.endsWith("A") }.toList() else listOf("AAA")
    }

    fun getStepsForEach(input: List<String>, imAGhost : Boolean = false, whileCondition: (String) -> Boolean) : List<Long> {
        val leftRightSequence = getLeftRightSequence(input)
        val map = getMap(input)

        val startPositions = getStartPositions(map.keys.toList(), imAGhost)
        val stepsForEach = ArrayList<Long>()

        startPositions.forEach { startPosition ->
            val stepForThisPosition = countMove(startPosition, map, leftRightSequence, whileCondition)
            stepsForEach.add(stepForThisPosition)
        }

        return stepsForEach
    }

    fun part1(input: List<String>): Long {
        val result = getStepsForEach(input, false) {it != "ZZZ"}
        return result[0]
    }



    fun part2(input: List<String>): Long {
        val steps = getStepsForEach(input, true) {!it.endsWith('Z') }

        val result = steps.reduce{acc, value ->
            leastCommonMultiple(acc, value)
        }

        return result
    }


    val testInput = readInput("Day08_test")
    val input = readInput("Day08")
    //check(part1(testInput) == 35)
    //check(part2(testInput) == 46)

    print("[TEST] DAY 08 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 08 Result : part 2 -> ")
    part2(testInput).println()

    print("DAY 08 Result : part 1 -> ")
    part1(input).println()

    print("DAY 08 Result : part 2 -> ")
    part2(input).println()
}