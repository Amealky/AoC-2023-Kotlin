
fun main() {

    class SeedDatas(var seed: Long) {
        var soil: Long = -1
        var fertilizer: Long = -1
        var water: Long = -1
        var light: Long = -1
        var temperature: Long = -1
        var humidity: Long = -1
        var location: Long = -1

        fun setValue(value:Long, section:String) {
            when(section) {
                "soil" -> soil = value
                "fertilizer" -> fertilizer = value
                "water" -> water = value
                "light" -> light = value
                "temperature" -> temperature = value
                "humidity" -> humidity = value
                "location" -> location = value
                else -> return
            }
        }

        fun getValue(section:String) : Long {
            return when(section) {
                "seed" -> seed
                "soil" -> soil
                "fertilizer" -> fertilizer
                "water" -> water
                "light" -> light
                "temperature" -> temperature
                "humidity" -> humidity
                "location" -> location
                else -> -1
            }
        }
        
        override fun toString() : String {
            return "Seed ${seed}, soil ${soil}, fertilizer ${fertilizer}," +
                    " water ${water}, light ${light}, temperature ${temperature}, humidity ${humidity}, " +
                    "location ${location}"
        }

    }

    fun getSectionTo(line:String) : String? {
        line.split("-").getOrNull(2)?.let {
            return it.split(" ")[0]
        }
        return null
    }

    fun getSectionFrom(line:String) : String? {
        val splittedLine = line.split("-")
        if(extractNumbers(splittedLine[0]).isEmpty()) {
            return splittedLine[0]
        }
        return null
    }

    fun seedIsConcerned(seed: Long, sourceRangeStart: Long, rangeLength: Long) : Boolean {
        val lastSeedUsed = sourceRangeStart + (rangeLength-1)
        if(seed >= sourceRangeStart && seed <= lastSeedUsed) {
            return true
        }
        return false
    }

    fun part1(input: List<String>): Long {
        var sectionTo = ""
        val seedsDatas = ArrayList<SeedDatas>()
        var sectionFrom = ""
        extractNumbers(input[0]).forEach {
            seedsDatas.add(SeedDatas(it.toLong()))
        }
        input.forEach {line ->
            getSectionTo(line)?.let {
                sectionTo = it
            }
            getSectionFrom(line)?.let {
                sectionFrom = it
            }

            if(sectionTo.isNotEmpty()) {
                val sectionInfos = extractNumbers(line)
                if(sectionInfos.isNotEmpty()) {
                    val destinationRangeStart = sectionInfos[0].toLong()
                    val sourceRangeStart = sectionInfos[1].toLong()
                    val rangeLength = sectionInfos[2].toLong()
                   // println(sectionTo)
                    seedsDatas.forEach {
                        //Get the value we need to check
                        val valueFrom = it.getValue(sectionFrom)

                        //Get the value we need to replace
                        val valueTo = it.getValue(sectionTo)
                        //If valueTo is not init init ( init with value from )
                        if(valueTo == -1L) {
                            it.setValue(valueFrom, sectionTo)
                        }

                        if(seedIsConcerned(valueFrom, sourceRangeStart, rangeLength)) {
                            val difference = valueFrom - sourceRangeStart
                            val correspondingDestination = destinationRangeStart + difference
                            it.setValue(correspondingDestination, sectionTo)
                        }
                    }
                }
            }

        }



        return  seedsDatas.minOf {
            it.location
        }
    }


    fun part2(input: List<String>): Long {
        //Get a script that work but too shame to be in public ( bruteforce method )
        //Need to be enhanced but no time for now
        return -1L
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    //check(part1(testInput) == 35)
    //check(part2(testInput) == 46)

    print("[TEST] DAY 05 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 05 Result : part 2 -> ")
    part2(testInput).println()

    print("DAY 05 Result : part 1 -> ")
    part1(input).println()

    print("DAY 05 Result : part 2 -> ")
    part2(input).println()
}