package org.updater.utils

object KmpScanner {
    fun scan(buffer: ByteArray, pattern: String): List<Int> {
        val patternBytes = parsePattern(pattern)
        val occurences = kmpSearch(buffer, patternBytes)

        return occurences
    }

    private fun parsePattern(pattern: String): ByteArray {
        val bytes = mutableListOf<Byte>()
        val hexDigits = pattern.split(" ")

        for (hex in hexDigits) {
            if (hex == "??") {
                bytes.add(-1)
            } else {
                bytes.add(hex.toInt(16).toByte())
            }
        }
        return bytes.toByteArray()
    }

    private fun kmpSearch(buf: ByteArray, pattern: ByteArray): List<Int> {
        val occurences = mutableListOf<Int>()
        val prefixTable = computePrefixTable(pattern)
        var j = 0

        for (i in buf.indices) {
            while (j > 0 && pattern[j].toInt() != -1 && buf[i] != pattern[j]) {
                j = prefixTable[j - 1]
            }
            if (pattern[j].toInt() == -1 || buf[i] == pattern[j]) {
                j++
            }
            if (j == pattern.size) {
                occurences.add(i - j + 1)
                j = prefixTable[j - 1]
            }
        }
        return occurences
    }

    private fun computePrefixTable(pattern: ByteArray): IntArray {
        val prefixTable = IntArray(pattern.size)
        var j = 0

        for (i in 1 until pattern.size) {
            while (j > 0 && pattern[j].toInt() != -1 && pattern[i] != pattern[j]) {
                j = prefixTable[j - 1]
            }
            if (pattern[j].toInt() == -1 || pattern[i] == pattern[j]) {
                j++
            }
            prefixTable[i] = j
        }
        return prefixTable
    }
}
