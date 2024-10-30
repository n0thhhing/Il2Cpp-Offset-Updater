package org.updater

import capstone.Capstone
import capstone.api.Disassembler
import capstone.api.DisassemblerFactory
import com.google.gson.Gson
import org.updater.utils.Color
import org.updater.utils.FileHandler
import org.updater.utils.KmpScanner
import org.updater.utils.OffsetFileParser
import org.updater.utils.getPattern

private const val CONFIG_FILE = "config.json"

var useVerbose = false

data class Config(
    val sigLen: Int = 64,
    val verbose: Boolean = false,
    val offsetFile: String = "offsets.txt",
    val outputFile: String = "out.txt",
    val newLib: String = "new.so",
    val oldLib: String = "old.so",
)

data class OffsetResult(val offset: String, val pattern: String, val foundOffsets: List<String>)

fun verbose(message: Any) {
    if (useVerbose) {
        println(message)
    }
}

fun main() {
    val startTime = System.currentTimeMillis()
    val config = loadConfig(CONFIG_FILE)

    useVerbose = config.verbose

    val offsets = OffsetFileParser.parse(config.offsetFile)
    val newLibBytes = FileHandler.readFileAsByteArr(config.newLib)
    val oldLibBytes = FileHandler.readFileAsByteArr(config.oldLib)

    var passCount = 0
    var failCount = 0

    val disassembler =
        DisassemblerFactory.createDisassembler(Capstone.CS_ARCH_ARM64, Capstone.CS_MODE_ARM).apply {
            setDetail(false)
        }

    FileHandler.writeFile(config.outputFile, "")

    offsets.forEach { offset ->
        val (result, found) = processOffset(disassembler, oldLibBytes, newLibBytes, offset, config)
        appendResultToFile(config.outputFile, result)
        if (found) passCount++ else failCount++
    }

    printSummary(startTime, offsets.size, passCount, failCount)
    disassembler.close()
}

private fun loadConfig(filename: String): Config {
    return Gson().fromJson(FileHandler.readFileAsStr(filename), Config::class.java)
}

private fun processOffset(
    cs: Disassembler,
    oldBytes: ByteArray,
    newBytes: ByteArray,
    offset: Int,
    config: Config,
): Pair<OffsetResult, Boolean> {
    val currentOffset = "0x${offset.toString(16)}"
    verbose(Color.green("Processing Offset: ${Color.blue(currentOffset)}"))

    val start = System.currentTimeMillis()
    val pattern = getPattern(cs, oldBytes, offset, config.sigLen)
    val occurrences = KmpScanner.scan(newBytes, pattern).map { "0x${it.toString(16)}" }
    val found = occurrences.isNotEmpty()
    verbose(
        if (occurrences.isNotEmpty()) Color.green("Pattern found at positions: ") + occurrences
        else Color.red("Pattern not found")
    )
    verbose(
        Color.green("Total processing time: ${Color.blue(System.currentTimeMillis() - start)}ms")
    )
    verbose("--------------------------------------")

    return Pair(OffsetResult(currentOffset, pattern, occurrences), found)
}

private fun appendResultToFile(filePath: String, result: OffsetResult) {
    val content =
        """
        Offset: ${result.offset}:
            - Pattern: ${result.pattern}
            - Found offsets: ${if (result.foundOffsets.isNotEmpty()) result.foundOffsets.joinToString(", ") else "None"}
    """
            .trimIndent()

    FileHandler.appendToFile(filePath, content)
}

private fun printSummary(startTime: Long, totalOffsets: Int, passCount: Int, failCount: Int) {
    val totalTime = System.currentTimeMillis() - startTime
    println(Color.green("Finished"))
    verbose(Color.green("\n$passCount patterns found"))
    verbose(Color.red("$failCount patterns not found"))
    verbose("$totalOffsets total (${Color.blue(totalTime)}ms)")
}
