import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.jupyter.api.libraries.JupyterIntegration
import java.io.BufferedReader
import java.io.File


open class Day(private val dayNum: Int) {
    val dataFolder = java.io.File("data")
    fun readDay(number: Int) = dataFolder.resolve("day$number.txt").readText().lines()
    protected val input by lazy { readDay(dayNum) }

    open fun part1(): Any {
        return 0
    }

    open fun part2(): Any {
        return 0
    }

    fun render(): Any {
        return dataFrameOf("Part", "Answer") ("#1", part1(), "#2", part2())
    }
}

fun day(number: Int, p1: (List<String>) -> Int, p2: (List<String>) -> Int): Day {
    return object : Day(number) {
        override fun part1(): Int {
            return p1(input)
        }
        override fun part2(): Int {
            return p2(input)
        }
    }
}

class AocIntegration: JupyterIntegration() {
    override fun Builder.onLoaded() {
        render<Day> { it.render() }
    }
}

fun solveInt(dayNumber: Int, part: Int, solution: BufferedReader.() -> Int) {
    val file = File("data/day$dayNumber.txt")
    val reader = file.bufferedReader()

    val result = solution(reader)
    reader.close()
    println("Day $dayNumber, part $part: $result")
}

fun solve(dayNumber: Int, solution: BufferedReader.() -> Unit) {
    val file = File("data/day$dayNumber.txt")
    val reader = file.bufferedReader()

    solution(reader)
    reader.close()
}
