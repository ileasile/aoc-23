fun main() {
    solve(7) {
        val inputLines = readLines()

        val cardRank = "AKQJT98765432".reversed().mapIndexed { index, c -> c to index }.toMap()

        class Hand(val cards: String) : Comparable<Hand> {
            override fun compareTo(other: Hand): Int {
                val kinds = kindsCount()
                val otherKinds = other.kindsCount()

                for (kind in 5 downTo 2) {
                    val r1 = kinds.getOrDefault(kind, 0)
                    val r2 = otherKinds.getOrDefault(kind, 0)

                    if (r1 != r2) {
                        return r1.compareTo(r2)
                    }
                }

                return simpleCompare(other)
            }

            fun kindsCount(): Map<Int, Int> {
                val charCounts = cards.groupBy { it }.mapValues { (c, chars) -> chars.size }.entries.toList()
                val kindsCounts = charCounts.groupBy { it.value }.mapValues { (count, char) -> char.size }
                return kindsCounts
            }

            fun simpleCompare(other: Hand): Int {
                for ((c1, c2) in cards.zip(other.cards)) {
                    val r1 = cardRank[c1]!!
                    val r2 = cardRank[c2]!!

                    if (r1 != r2) {
                         return r1.compareTo(r2)
                    }
                }
                return 0
            }
        }
        class HandWithBid(val hand: Hand, val bid: Long)

        val handsWithBids = inputLines.map { line ->
            val (hand, bidStr) = line.split(" ")
            HandWithBid(Hand(hand), bidStr.toLong())
        }

        handsWithBids.sortedBy { it.hand }.take(100).forEach {
            println("${it.hand.cards}: ${it.hand.kindsCount()}")
        }

        val result = handsWithBids.sortedBy { it.hand }.mapIndexed { index, handWithBid ->
            (index + 1) * handWithBid.bid
        }.sum()

        println(result)
    }
}
