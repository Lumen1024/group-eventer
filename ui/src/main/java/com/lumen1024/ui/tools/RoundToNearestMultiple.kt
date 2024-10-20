package com.lumen1024.ui.tools

/**
 * Rounds this integer to the nearest multiple of the given [multiple].
 *
 * If this integer is exactly halfway between two multiples, it is rounded up.
 *
 * @param multiple The multiple to round to.
 * @return The rounded integer.
 * @throws IllegalArgumentException if [multiple] is 0.
 */
fun Int.roundToNearestMultiple(multiple: Int): Int {
    val remainder = this % multiple
    return if (remainder < multiple / 2) {
        this - remainder
    } else {
        this + (multiple - remainder)
    }
}