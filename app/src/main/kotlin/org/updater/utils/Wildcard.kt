package org.updater.utils

import capstone.Arm64_const.*
import capstone.api.Instruction

const val ADD: Int = 4
const val BR: Int = 52
const val ADRP = 13
const val BL = 46
const val B = 39
const val CBNZ = 85
const val CBZ = 86
const val CSEL = 138
const val FCMP = 190
const val LDP = 391
const val TBZ = 836
const val TBNZ = 834
const val LDRB = 396
const val LDR = 393
const val STRB = 763
const val STR = 762

data class InstructionCases(
    val wildCards: Set<Int>,
    val specialCases: SpecialCases,
    val safeInstructions: Set<Int>,
)

data class SpecialCases(val ldr: Set<Int>, val str: Set<Int>)

val instructions =
    InstructionCases(
        wildCards =
            setOf(
                ARM64_INS_ADRP,
                ARM64_INS_BL,
                ARM64_INS_B,
                ARM64_INS_CBNZ,
                ARM64_INS_CBZ,
                ARM64_INS_CSEL,
                ARM64_INS_FCMP,
                ARM64_INS_LDP,
                ARM64_INS_TBZ,
                ARM64_INS_TBNZ,
            ),
        specialCases =
            SpecialCases(ldr = setOf(ARM64_INS_LDRB, ARM64_INS_LDR), str = setOf(ARM64_INS_STRB)),
        safeInstructions = setOf(ARM64_INS_STR),
    )

val wildCardIds: Set<Int> = instructions.wildCards
val ldrCardIds: Set<Int> = instructions.specialCases.ldr
val strCardIds: Set<Int> = instructions.specialCases.str

fun String.commaCount(): Int = this.count { it == ',' }

fun insnIsWildCard(insn: Instruction): WildCardResult {
    val operand = insn.opStr
    val id = insn.id
    var isWildCard: Boolean
    var specialByte: Boolean

    when {
        id in wildCardIds -> {
            isWildCard = true
            specialByte = false
        }
        id in ldrCardIds &&
            operand.commaCount() > 1 &&
            !Regex("""/#\-0x[A-Fa-f0-9]+""").containsMatchIn(operand) -> {
            isWildCard = true
            specialByte = true
        }
        id in strCardIds &&
            !operand.contains("wzr") &&
            !operand.contains("w9") &&
            !operand.contains("w20") -> {
            isWildCard = true
            specialByte = true
        }
        id == ARM64_INS_ADD && !operand.contains("sp") -> {
            isWildCard = true
            specialByte = true
        }
        else -> {
            isWildCard = false
            specialByte = false
        }
    }

    return WildCardResult(isWildCard, specialByte)
}

data class WildCardResult(val isWildCard: Boolean, val specialByte: Boolean)
