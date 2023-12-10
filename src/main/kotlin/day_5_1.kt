fun main() {
    solve(5) {
        val seeds = readLine().substringAfter(": ").split(" ").map { it.toLong() }
        readLine()

        class Interval(val to: Long, val from: Long, val length: Long)

        val mapping = mutableListOf<List<Interval>>()

        while (true) {
            if (readLine() == null) break
            val curMap = mutableListOf<Interval>()
            while (true) {
                val line = readLine()
                if (line == null || line.isBlank()) break
                val (to, from, length) = line.split(" ")
                curMap.add(Interval(to.toLong(), from.toLong(), length.toLong()))
            }
            mapping.add(curMap)
        }

        val result = seeds.minOf { seed ->
            var cur = seed
            for (map in mapping) {
                for (interval in map) {
                    if (cur in interval.from ..< (interval.from + interval.length)) {
                        cur = interval.to + (cur - interval.from)
                        break
                    }
                }
            }

            cur
        }

        println(result)
    }
}