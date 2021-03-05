package mr.bergin.unposter.model

import arrow.core.Validated
import arrow.core.extensions.applicativeNel
import arrow.core.extensions.validated.functor.map
import arrow.core.invalidNel
import arrow.core.valid

sealed class Choice(
    val display: String,
    val explanation: String,
) {
    companion object {
        internal fun <K : Choice> create(
            display: String,
            explanation: String,
            factory: (String, String) -> K,
        ) = Validated.applicativeNel<ChoiceError>().tupledN(
            ChoiceDisplayIsBlank.validate(display),
            ChoiceExplanationIsBlank.validate(explanation)
        ).map {
            factory(display, explanation)
        }
    }
}
class IncorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
    companion object {
        operator fun invoke(display: String, explanation: String) = create(display, explanation, ::IncorrectChoice)
    }
}
class CorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
    companion object {
        operator fun invoke(display: String, explanation: String) = create(display, explanation, ::CorrectChoice)
    }
}

sealed class ChoiceError
object ChoiceDisplayIsBlank : ChoiceError() {
    fun validate(display: String) = if (display.isBlank()) {
        this.invalidNel()
    } else {
        display.valid()
    }
}
object ChoiceExplanationIsBlank : ChoiceError() {
    fun validate(explanation: String) = if (explanation.isBlank()) {
        this.invalidNel()
    } else {
        explanation.valid()
    }
}
