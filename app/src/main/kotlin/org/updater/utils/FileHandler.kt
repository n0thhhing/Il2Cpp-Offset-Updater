package org.updater.utils

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

object FileHandler {
    fun readFileAsStr(filePath: String): String {
        val file = File(filePath)
        val content = StringBuilder()

        try {
            BufferedReader(FileReader(file)).use { reader ->
                reader.lineSequence().forEach { line -> content.appendLine(line) }
            }
        } catch (e: IOException) {
            println("Error reading file as String: ${e.message}")
        }

        return content.toString()
    }

    fun readFileAsByteArr(filePath: String): ByteArray {
        val file = File(filePath)

        return try {
            file.readBytes()
        } catch (e: IOException) {
            println("Error reading file as ByteArray: ${e.message}")
            ByteArray(0)
        }
    }

    fun writeFile(filePath: String, content: String) {
        try {
            BufferedWriter(FileWriter(filePath)).use { writer -> writer.write(content) }
        } catch (e: IOException) {
            println("Error writing to file: ${e.message}")
        }
    }

    fun appendToFile(filePath: String, content: String) {
        try {
            BufferedWriter(FileWriter(filePath, true)).use { writer ->
                writer.append(content)
                writer.newLine()
            }
        } catch (e: IOException) {
            println("Error appending to file: ${e.message}")
        }
    }
}
