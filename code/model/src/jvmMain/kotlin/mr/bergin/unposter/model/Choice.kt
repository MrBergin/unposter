package mr.bergin.unposter.model

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success

sealed class Choice(
    val display: String,
    val explanation: String,
) {
    companion object {
        internal fun <K : Choice> create(
            display: String,
            explanation: String,
            factory: (String, String) -> K,
        ): Result<Choice, List<ChoiceError>> {
            val errors = listOf(
                ChoiceDisplayIsBlank.validate(display),
                ChoiceExplanationIsBlank.validate(explanation),
            ).filterIsInstance<Failure<ChoiceError>>().map { it.reason }
            if (errors.isNotEmpty()) {
                return Failure(errors)
            } else {
                return Success(factory(display, explanation))
            }
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
        Failure(this)
    } else {
        Success(display)
    }
}

object ChoiceExplanationIsBlank : ChoiceError() {
    fun validate(explanation: String) = if (explanation.isBlank()) {
        Failure(this)
    } else {
        Success(explanation)
    }
}
