fun main() {

    fun isPossible(ballsCount : Int, ballsColor: String): Boolean {
         return when(ballsColor) {
            "red" -> ballsCount <= 12
            "green" -> ballsCount <= 13
            "blue" -> ballsCount <= 14
             else -> false
         }
    }

    fun splitDrawPerColor(draw: String, callback: (ballsCount : Int, ballsColor: String) -> Unit) {
        //Regex qui va découper dans chaque game le numéro associé à la couleur
        val regex = Regex("([0-9]+) (blue|green|red)")
        regex.findAll(draw).forEach {
            callback(it.groupValues[1].toInt(), it.groupValues[2])
        }
    }

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach {line ->
            val game = line.split(":")
            val gameIndex = game[0].split(" ")[1]

            //We assume that draw are valid per default
            var drawsAreValids = true

            splitDrawPerColor(game[1]) { ballsCount, ballsColor ->
                if(!isPossible(ballsCount, ballsColor)) {
                    drawsAreValids = false
                    return@splitDrawPerColor
                }
            }

            if(drawsAreValids) result += gameIndex.toInt()

        }
        return result
    }

    fun part2(input: List<String>): Int {
        var totalPower = 0
        input.forEach { line ->
            var greaterBlue = 0
            var greaterRed = 0
            var greaterGreen = 0
            splitDrawPerColor(line){ ballsCount, ballsColor ->
                when(ballsColor) {
                    "blue" -> if(ballsCount > greaterBlue) greaterBlue = ballsCount
                    "red" -> if(ballsCount > greaterRed) greaterRed = ballsCount
                    "green" -> if(ballsCount > greaterGreen) greaterGreen = ballsCount
                }
            }
            val drawPower = greaterBlue * greaterRed * greaterGreen
            totalPower += drawPower
        }
        return totalPower
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    print("[TEST] DAY 02 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 02 Result : part 2 -> ")
    part2(testInput).println()

    val input = readInput("Day02")
    print("DAY 02 Result : part 1 -> ")
    part1(input).println()

    print("DAY 02 Result : part 2 -> ")
    part2(input).println()
}