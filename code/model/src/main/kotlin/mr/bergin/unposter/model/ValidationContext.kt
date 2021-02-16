package mr.bergin.unposter.model

import arrow.core.Nel
import arrow.core.Validated
import arrow.core.invalid
import arrow.core.valid

/**
 * Not a big fan of this, again just playing around with how to express validation in arrow
 */
data class ValidationContext(val predicate: () -> Boolean) {
    infix fun <Err> then(error: () -> Err): Validated<Nel<Err>, ValidationContext> {
        return if (predicate()) error().invalid().toValidatedNel() else valid()
    }
}

fun `if`(predicate: () -> Boolean): ValidationContext {
    return ValidationContext(predicate)
}