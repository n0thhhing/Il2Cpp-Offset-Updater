package org.updater.utils

enum class Foreground(val code: String) {
    Black("\u001B[30m"),
    Red("\u001B[31m"),
    Green("\u001B[32m"),
    Yellow("\u001B[33m"),
    Blue("\u001B[34m"),
    Magenta("\u001B[35m"),
    Cyan("\u001B[36m"),
    White("\u001B[37m"),
    BrightBlack("\u001B[90m"),
    BrightRed("\u001B[91m"),
    BrightGreen("\u001B[92m"),
    BrightYellow("\u001B[93m"),
    BrightBlue("\u001B[94m"),
    BrightMagenta("\u001B[95m"),
    BrightCyan("\u001B[96m"),
    BrightWhite("\u001B[97m"),
    Grey("\u001B[90m"),
    Gray("\u001B[90m"),
    LightGray("\u001B[38;5;248m"),
    DarkGray("\u001B[38;5;240m"),
    Brown("\u001B[38;5;130m"),
    Olive("\u001B[38;5;58m"),
    Navy("\u001B[38;5;18m"),
}

enum class Background(val code: String) {
    Black("\u001B[40m"),
    Red("\u001B[41m"),
    Green("\u001B[42m"),
    Yellow("\u001B[43m"),
    Blue("\u001B[44m"),
    Magenta("\u001B[45m"),
    Cyan("\u001B[46m"),
    White("\u001B[47m"),
    BrightBlack("\u001B[100m"),
    BrightRed("\u001B[101m"),
    BrightGreen("\u001B[102m"),
    BrightYellow("\u001B[103m"),
    BrightBlue("\u001B[104m"),
    BrightMagenta("\u001B[105m"),
    BrightCyan("\u001B[106m"),
    BrightWhite("\u001B[107m"),
    LightGray("\u001B[48;5;248m"),
    DarkGray("\u001B[48;5;240m"),
    Brown("\u001B[48;5;130m"),
    Olive("\u001B[48;5;58m"),
    Navy("\u001B[48;5;18m"),
}

enum class Modifiers(val code: String) {
    Reset("\u001B[0m"),
    Bold("\u001B[1m"),
    Dim("\u001B[2m"),
    Italic("\u001B[3m"),
    Underline("\u001B[4m"),
    Blink("\u001B[5m"),
    Reverse("\u001B[7m"),
    Hidden("\u001B[8m"),
}

object Color {
    private fun colorText(text: Any, color: String): String = "$color$text${Modifiers.Reset.code}"

    private fun applyColors(txt: Any, color: String): String {
        val text = "$txt"
        val sb = StringBuilder()
        var lastIndex = 0

        while (true) {
            val resetIndex = text.indexOf(Modifiers.Reset.code, lastIndex)
            if (resetIndex == -1) {
                sb.append(colorText(text.substring(lastIndex), color))
                break
            }
            sb.append(colorText(text.substring(lastIndex, resetIndex), color))
            sb.append(Modifiers.Reset.code)
            lastIndex = resetIndex + Modifiers.Reset.code.length
        }

        return sb.toString()
    }

    fun black(text: Any): String = applyColors(text, Foreground.Black.code)

    fun red(text: Any): String = applyColors(text, Foreground.Red.code)

    fun green(text: Any): String = applyColors(text, Foreground.Green.code)

    fun yellow(text: Any): String = applyColors(text, Foreground.Yellow.code)

    fun blue(text: Any): String = applyColors(text, Foreground.Blue.code)

    fun magenta(text: Any): String = applyColors(text, Foreground.Magenta.code)

    fun cyan(text: Any): String = applyColors(text, Foreground.Cyan.code)

    fun white(text: Any): String = applyColors(text, Foreground.White.code)

    fun brightBlack(text: Any): String = applyColors(text, Foreground.BrightBlack.code)

    fun brightRed(text: Any): String = applyColors(text, Foreground.BrightRed.code)

    fun brightGreen(text: Any): String = applyColors(text, Foreground.BrightGreen.code)

    fun brightYellow(text: Any): String = applyColors(text, Foreground.BrightYellow.code)

    fun brightBlue(text: Any): String = applyColors(text, Foreground.BrightBlue.code)

    fun brightMagenta(text: Any): String = applyColors(text, Foreground.BrightMagenta.code)

    fun brightCyan(text: Any): String = applyColors(text, Foreground.BrightCyan.code)

    fun brightWhite(text: Any): String = applyColors(text, Foreground.BrightWhite.code)

    fun grey(text: Any): String = applyColors(text, Foreground.Grey.code)

    fun gray(text: Any): String = applyColors(text, Foreground.Gray.code)

    fun lightGray(text: Any): String = applyColors(text, Foreground.LightGray.code)

    fun darkGray(text: Any): String = applyColors(text, Foreground.DarkGray.code)

    fun brown(text: Any): String = applyColors(text, Foreground.Brown.code)

    fun olive(text: Any): String = applyColors(text, Foreground.Olive.code)

    fun navy(text: Any): String = applyColors(text, Foreground.Navy.code)

    fun bold(text: Any): String = applyColors(text, Modifiers.Bold.code)

    fun dim(text: Any): String = applyColors(text, Modifiers.Dim.code)

    fun italic(text: Any): String = applyColors(text, Modifiers.Italic.code)

    fun underline(text: Any): String = applyColors(text, Modifiers.Underline.code)

    fun blink(text: Any): String = applyColors(text, Modifiers.Blink.code)

    fun reverse(text: Any): String = applyColors(text, Modifiers.Reverse.code)

    fun hidden(text: Any): String = applyColors(text, Modifiers.Hidden.code)

    object Bg {
        fun black(text: Any): String = applyColors(text, Background.Black.code)

        fun red(text: Any): String = applyColors(text, Background.Red.code)

        fun green(text: Any): String = applyColors(text, Background.Green.code)

        fun yellow(text: Any): String = applyColors(text, Background.Yellow.code)

        fun blue(text: Any): String = applyColors(text, Background.Blue.code)

        fun magenta(text: Any): String = applyColors(text, Background.Magenta.code)

        fun cyan(text: Any): String = applyColors(text, Background.Cyan.code)

        fun white(text: Any): String = applyColors(text, Background.White.code)

        fun brightBlack(text: Any): String = applyColors(text, Background.BrightBlack.code)

        fun brightRed(text: Any): String = applyColors(text, Background.BrightRed.code)

        fun brightGreen(text: Any): String = applyColors(text, Background.BrightGreen.code)

        fun brightYellow(text: Any): String = applyColors(text, Background.BrightYellow.code)

        fun brightBlue(text: Any): String = applyColors(text, Background.BrightBlue.code)

        fun brightMagenta(text: Any): String = applyColors(text, Background.BrightMagenta.code)

        fun brightCyan(text: Any): String = applyColors(text, Background.BrightCyan.code)

        fun brightWhite(text: Any): String = applyColors(text, Background.BrightWhite.code)

        fun lightGray(text: Any): String = applyColors(text, Background.LightGray.code)

        fun darkGray(text: Any): String = applyColors(text, Background.DarkGray.code)

        fun brown(text: Any): String = applyColors(text, Background.Brown.code)

        fun olive(text: Any): String = applyColors(text, Background.Olive.code)

        fun navy(text: Any): String = applyColors(text, Background.Navy.code)
    }
}
