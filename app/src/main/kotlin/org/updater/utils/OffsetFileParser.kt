package org.updater.utils

import java.io.File

object OffsetFileParser {

    fun parse(filePath: String): List<Int> {
        val offsets = mutableListOf<Int>()
        val file = File(filePath)

        if (!file.exists()) {
            throw IllegalArgumentException("File not found: $filePath")
        }

        file.forEachLine { line ->
            val tokens = tokenize(line)
            if (tokens.isNotEmpty()) {
                for (token in tokens) {
                    if (token.startsWith("0x")) {
                        val offset = token.substring(2).toIntOrNull(16)
                        if (offset != null) {
                            offsets.add(offset)
                        }
                    }
                }
            }
        }

        return offsets
    }

    private fun tokenize(line: String): List<String> {
        val cleanLine = line.substringBefore("//").trim()
        return cleanLine.split("\\s+".toRegex()).filter { it.isNotEmpty() }
    }
}
