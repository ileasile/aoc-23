private val memo = mutableMapOf<Pair<Int, Int>, Long>()

fun Cn(n: Int, k: Int): Long {
    if (k == 0 || k == n) return 1
    if (k > n - k) return Cn(n, n - k) // Use symmetry property

    val key = Pair(n, k)
    if (memo.containsKey(key)) {
        return memo[key]!!
    }

    val result = Cn(n - 1, k - 1) + Cn(n - 1, k)
    memo[key] = result
    return result
}
