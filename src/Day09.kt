fun main() {


    fun getDifferenceTree(initialNumbers: ArrayList<Long>) : ArrayList<ArrayList<Long>> {
        val treeNumbers = ArrayList<ArrayList<Long>>()
        var tempNumbers = initialNumbers
        treeNumbers.add(tempNumbers)

        while(!tempNumbers.onlyContains(0)) {
            val differencesList = ArrayList<Long>()
            for(i in 1 until tempNumbers.size) {
                val diff = tempNumbers[i] - tempNumbers[i - 1]
                differencesList.add(diff)
            }

            treeNumbers.add(differencesList)
            tempNumbers = differencesList
        }

        return treeNumbers
    }
    
    fun part1(input: List<String>): Long {
        var result = 0L
        input.forEach {line ->
            //A tree of difference for a line
            val treeNumbers = getDifferenceTree(ArrayList(extractNumbers(line).map{ it.toLong()}))
            treeNumbers.last().add(0)
            val reversedTreeNumbers = treeNumbers.reversed()
            reversedTreeNumbers.forEachIndexed { index, numbers ->
                if(index > 0) reversedTreeNumbers[index].add(numbers.last() + reversedTreeNumbers[index - 1].last())
            }

            result += treeNumbers[0].last()
        }
        println("---RESULT---")
        return result
    }


    fun part2(input: List<String>): Long {
        var result = 0L
        input.forEach {line ->
            //A tree of difference for a line
            val treeNumbers = getDifferenceTree(ArrayList(extractNumbers(line).map{ it.toLong()}))
            treeNumbers.last().add(0, 0)

            val reversedTreeNumbers = treeNumbers.reversed()
            reversedTreeNumbers.forEachIndexed { index, numbers ->
                if(index > 0) reversedTreeNumbers[index].add(0, numbers.first() - reversedTreeNumbers[index - 1].first())
            }

            result += treeNumbers[0].first()
        }
        println("---RESULT---")
        return result
    }


    val testInput = readInput("Day09_test")
    val input = readInput("Day09")
    //check(part1(testInput) == 35)
    //check(part2(testInput) == 46)

    print("[TEST] DAY 09 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 09 Result : part 2 -> ")
    part2(testInput).println()

    print("DAY 09 Result : part 1 -> ")
    part1(input).println()

    print("DAY 09 Result : part 2 -> ")
    part2(input).println()
}