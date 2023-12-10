
fun main() {
    solve(4) {
        val inputLines = readLines()

        class Game(
            val id: Int,
            val card: List<Int>,
            val myNumbers: List<Int>,
        )

        val games = inputLines.map { line ->
            val (idPartStr, playPartStr) = line.split(':')
            val gameId = idPartStr.substringAfter("Card").trim().toInt()
            val (cardStr, myStr) = playPartStr.trim().split(" | ")

            val regex = Regex(" +")

            val card = cardStr.trim().split(regex).map { it.trim().toInt() }
            val myNumbers = myStr.trim().split(regex).map { it.trim().toInt() }

            Game(gameId, card, myNumbers)
        }

        val copiesNum = LongArray(games.size) { 1L }

        val result = games.sumOf { game ->
            val p = game.card.count { it in game.myNumbers }
            val myCopies = copiesNum[game.id - 1]
            for (gi in game.id ..< game.id + p) {
                if (gi > games.lastIndex) break
                copiesNum[gi] += myCopies
            }
            myCopies
        }

        println(result)
    }
}
