fun main() {
    solve(5) {
        data class SeedsRange(val from: Long, val length: Long) {
            fun intersect(otherFrom: Long, otherLength: Long): SeedsRange? {
                return if (from in otherFrom ..< (otherFrom + otherLength)) {
                    SeedsRange(from, minOf(length, otherFrom + otherLength - from))
                } else if (otherFrom in from ..< (from + length)) {
                    SeedsRange(otherFrom, minOf(otherLength, from + length - otherFrom))
                } else {
                    null
                }
            }
        }

        val seedsRanges = readLine().substringAfter(": ")
            .split(" ").map { it.toLong() }
            .windowed(2, 2) { SeedsRange(it[0], it[1]) }

        readLine()

        data class Interval(val to: Long, val from: Long, val length: Long)

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

        //println(seedsRanges)

        val result = seedsRanges.minOf { seedRange ->
            var cur = mutableListOf(seedRange)
            for (map in mapping) {
                val next = mutableListOf<SeedsRange>()
                for (mapInterval in map) {
                    for (curInterval in cur) {
                        val intersection = curInterval.intersect(mapInterval.from, mapInterval.length) ?: continue
                        val transferK = mapInterval.to - mapInterval.from
                        next.add(SeedsRange(intersection.from + transferK, intersection.length))
                    }
                }
                cur = next
            }

            cur.minOfOrNull { it.from } ?: Long.MAX_VALUE
        }

        println(result)
    }
}