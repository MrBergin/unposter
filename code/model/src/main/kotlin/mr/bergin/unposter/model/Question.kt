package mr.bergin.unposter.model

import arrow.core.*
import arrow.core.extensions.applicativeNel
import mr.bergin.unposter.model.Choice.CorrectChoice
import mr.bergin.unposter.model.MultipleChoiceQuestion.Companion.Error.NotEnoughCorrectChoices
import mr.bergin.unposter.model.MultipleChoiceQuestion.Companion.Error.NotEnoughInCorrectChoices

/**
 * This exists until I better understand how to utilise error validation in Arrow.
 */
fun <Type, Error> Type.validate(predicate: () -> Boolean, error: () -> Error): Validated<Nel<Error>, Type> {
    return if (predicate()) error().invalid().toValidatedNel() else valid()
}

sealed class Question

class MultipleChoiceQuestion private constructor(
    val display: String,
    val choices: List<Choice>,
) : Question() {

    companion object {
        sealed class Error {
            object BlankDisplay : Error()
            data class NotEnoughCorrectChoices(val choices: List<Choice>) : Error()
            data class NotEnoughInCorrectChoices(val choices: List<Choice>) : Error()
        }

        operator fun invoke(
            display: String,
            choices: List<Choice>,
        ) = Validated.applicativeNel<Error>().tupledN(
            validate({ display.isBlank() }, { Error.BlankDisplay }),
            validate({ choices.none { it is CorrectChoice } }, { NotEnoughCorrectChoices(choices) }),
            validate({ choices.none { it is Choice.IncorrectChoice } }, { NotEnoughInCorrectChoices(choices) }),
        ).fix().map { MultipleChoiceQuestion(display, choices) }
    }
}

sealed class Choice(val display: String, val explanation: String) {

    companion object {
        sealed class Error {
            object BlankDisplay : Error()
            object BlankExplanation : Error()
        }

        private fun validate(display: String, explanation: String) = Validated.applicativeNel<Error>().tupledN(
            validate({ display.isBlank() }, { Error.BlankDisplay }),
            validate({ explanation.isBlank() }, { Error.BlankExplanation })
        ).fix()
    }

    class IncorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
        companion object {
            operator fun invoke(display: String, explanation: String) =
                validate(display, explanation).map { IncorrectChoice(display, explanation) }
        }
    }

    class CorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
        companion object {
            operator fun invoke(display: String, explanation: String) =
                validate(display, explanation).map { CorrectChoice(display, explanation) }
        }
    }
}

