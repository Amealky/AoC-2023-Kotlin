enum class Type {
    FIVE,
    FOUR,
    FULL_HOUSE,
    THREE,
    TWO,
    ONE,
    HIGH
}

fun main() {

    class Hand(var cards: String, var bid:Int) {
        lateinit var cardsDatas : Map<Char, Int>
        var hand_type : Type = Type.HIGH

        fun cardsDatasJokerised() : Map<Char, Int> {
            if(!containOnlyJoker() && containAtLeastOneJoker()) {
                val cardsDatasJokerised = cardsDatas.toMutableMap()

                cardsDatas.forEach { card, count ->
                    val (cardToDuplicate, _) = getCardDataWithMaxExceptJoker()
                    cardsDatasJokerised[cardToDuplicate] = cardsDatas[cardToDuplicate]!! + cardsDatas['J']!!
                    cardsDatasJokerised.remove('J')
                }

                return cardsDatasJokerised
            }

            return cardsDatas

        }

        fun getCardDataWithMaxExceptJoker() : Pair<Char, Int> {
            return cardsDatas.entries.filter { it.key != 'J' }.maxBy { it.value }.toPair()
        }

        fun containOnlyJoker() : Boolean {
            return cards.onlyContains('J')
        }

        fun containAtLeastOneJoker() : Boolean {
            return cards.contains('J')
        }
    }


    fun getHandDatas(hand: String, joker_rule:Boolean = false) : Map<Char, Int> {
        val cardsData = mutableMapOf<Char, Int>()

        for (card in hand) {
            cardsData[card] = cardsData.getOrDefault(card, 0) + 1
        }


        return cardsData
    }

    fun getHandType(cardsDatas: Map<Char, Int>) : Type {
        val number_of_same_cards = cardsDatas.size
        val highest_number_of_same_cards = cardsDatas.values.max()
        return when (number_of_same_cards) {
            1 -> {
                Type.FIVE
            }
            2 -> {
                if(highest_number_of_same_cards == 4) Type.FOUR else Type.FULL_HOUSE
            }
            3 -> {
                if(highest_number_of_same_cards == 3) Type.THREE else Type.TWO
            }
            4 -> {
                Type.ONE
            }
            else -> Type.HIGH
        }

    }

    fun getCardValue(card: Char, jokerRule: Boolean = false): Char {
        val characters = listOf('A', 'K', 'Q', 'J', 'T')

        if(!characters.contains(card)) return card

        val priorities = if(jokerRule)  {
            listOf('E', 'D', 'C', '1', 'A',)
        } else {
            listOf('E', 'D', 'C', 'B', 'A')
        }

        return priorities[characters.indexOf(card)]
    }

    fun solve(input: List<String>, joker_rule : Boolean = false) : Int{
        val hands = ArrayList<Hand>()
        input.forEach { line ->
            val (handCards, bids) = line.split(" ")
            val hand = Hand(handCards, bids.toInt())
            hand.cardsDatas = getHandDatas(handCards, joker_rule)
            hand.hand_type = if(joker_rule) getHandType(hand.cardsDatasJokerised()) else getHandType(hand.cardsDatas)
            hands.add(hand)
        }

        hands.sortByDescending {
            it.hand_type
        }

        var rank = 1
        var totalBids = 0
        hands.groupBy { it.hand_type }.forEach { (type, handsOfType) ->
            val comparator = compareBy<Hand> { hand ->
                hand.cards.map { char -> getCardValue(char, joker_rule) }.joinToString(separator = "") // Concaténer les priorités
            }

            val sortedHands = handsOfType.sortedWith(comparator)

            sortedHands.forEach {
                totalBids += it.bid * rank
                rank++
            }


        }

        return totalBids
    }

    fun part1(input: List<String>): Int {
        return solve(input)
    }


    fun part2(input: List<String>): Int {
        return solve(input, true)
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")
    //check(part1(testInput) == 35)
    //check(part2(testInput) == 46)

    print("[TEST] DAY 07 Result : part 1 -> ")
    part1(testInput).println()

    print("[TEST] DAY 07 Result : part 2 -> ")
    part2(testInput).println()

    print("DAY 07 Result : part 1 -> ")
    part1(input).println()

    print("DAY 07 Result : part 2 -> ")
    part2(input).println()
}