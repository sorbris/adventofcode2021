package utils

fun String.parseBinary(): Int =
    fold(0) { acc, c ->
        (acc shl 1) or c.digitToInt()
    }
