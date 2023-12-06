
fun main() {


    fun getPossibilities(raceTime: Long, recordDistance:Long) : Int {
        var possibilitiesToWin = 0
       for(i in 0 until raceTime) {
           val speedPerMs = i
           val durationLeft = raceTime - i
           val result = speedPerMs * durationLeft
           if(result > recordDistance) possibilitiesToWin++
       }

        return possibilitiesToWin
    }

    fun extractTimeAndDistance(input: List<String>): Map<String, String> {
        var times: List<String> = listOf()
        var distances: List<String> = listOf()
        input.forEach { line ->
            val splittedLine = line.split(":")
            val section = splittedLine[0]
            when (section) {
                "Time" -> times = extractNumbers(splittedLine[1]).map { it }
                "Distance" -> distances = extractNumbers(splittedLine[1]).map { it }
            }
        }
        return times.zip(distances).toMap()
    }

    fun concatenateStrings(values: List<String>) : String {
        return values.reduce { acc, s -> acc+s }
    }

    fun part1(input: List<String>): Long {
        val timesAndDistances = extractTimeAndDistance(input)
        var result = 1L
        timesAndDistances.forEach { time, distance ->
            result *= getPossibilities(time.toLong(), distance.toLong())
        }
       return result
    }


    fun part2(input: List<String>): Long {
        val timesAndDistances = extractTimeAndDistance(input)
        var result = 1L

        result *= getPossibilities(concatenateStrings(timesAndDistances.keys.toList()).toLong(),
                concatenateStrings(timesAndDistances.values.toList()).toLong())

        return result
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")
    //check(part1(testInput) == 35)
    //check(part2(testInput) == 46)

    print("[TEST] DAY 06 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 06 Result : part 2 -> ")
    part2(testInput).println()

    print("DAY 06 Result : part 1 -> ")
    part1(input).println()

    print("DAY 06 Result : part 2 -> ")
    part2(input).println()
}