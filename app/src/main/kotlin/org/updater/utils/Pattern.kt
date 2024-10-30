package org.updater.utils

import capstone.api.Disassembler

fun getPattern(cs: Disassembler, buf: ByteArray, offset: Int, len: Int): String {
    val hex = HexUtils.getHexFromOffset(buf, offset, len)
    val instructions = cs.disasm(hex, offset.toLong())
    var pattern = StringBuilder()

    for (insn in instructions) {
        val result = insnIsWildCard(insn)
        val bytesAsHex: String =
            if (result.isWildCard) {
                if (result.specialByte) {
                    "?? ?? ?? ${String.format("%02x", insn.bytes[3])}" // Insert space between
                    // wildcards
                } else {
                    "?? ?? ?? ??" // Insert space for full wildcards
                }
            } else {
                HexUtils.byteArraytoHexString(insn.bytes).chunked(2).joinToString(" ")
            }
        pattern.append(bytesAsHex).append(" ") // Append the bytes and space
    }

    return pattern.toString().trim()
}
