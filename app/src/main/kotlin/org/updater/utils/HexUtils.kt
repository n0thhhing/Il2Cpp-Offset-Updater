package org.updater.utils

object HexUtils {
    fun hexStringToByteArray(hexString: String): ByteArray {
        check(hexString.length % 2 == 0) { "Must have an even length" }

        return ByteArray(hexString.length / 2) {
            Integer.parseInt(hexString, it * 2, (it + 1) * 2, 16).toByte()
        }
    }

    fun byteArraytoHexString(buf: ByteArray): String {
        val hexStringBuilder = StringBuilder()
        for (byte in buf) {
            hexStringBuilder.append(String.format("%02x", byte))
        }
        return hexStringBuilder.toString()
    }

    fun getHexFromOffset(buf: ByteArray, offset: Int, len: Int): ByteArray {
        if (offset < 0 || offset + len > buf.size) {
            throw IllegalArgumentException("Offset and length are out of bounds")
        }

        return ByteArray(len).apply { System.arraycopy(buf, offset, this, 0, len) }
    }
}
