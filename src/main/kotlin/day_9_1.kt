fun main() {
    solve(9) {
        val inputLines = readLines()
        val seqs = inputLines.map { line ->
            line.split(" ").map { it.toLong() }
        }

        val result: Long = seqs.sumOf { initialSeq ->
            val lastNums = mutableListOf<Long>()

            var seq = initialSeq
            while(true) {
                lastNums.add(seq.last())
                if (seq.all { it == 0L }) break
                seq = seq.windowed(2) { it[1] - it[0] }
            }

            lastNums.sum()
        }

        println(result)
    }
}