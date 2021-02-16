package mr.bergin.unposter.model

import arrow.core.*
import arrow.core.extensions.applicativeNel
import arrow.core.extensions.validated.functor.map
import mr.bergin.unposter.model.Choice.CorrectChoice
import mr.bergin.unposter.model.Choice.IncorrectChoice
import mr.bergin.unposter.model.MultipleChoiceQuestion.Companion.Error.NotEnoughCorrectChoices
import mr.bergin.unposter.model.MultipleChoiceQuestion.Companion.Error.NotEnoughInCorrectChoices

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
            `if` { display.isBlank() } then { Error.BlankDisplay },
            `if` { choices.none { it is CorrectChoice } } then { NotEnoughCorrectChoices(choices) },
            `if` { choices.none { it is IncorrectChoice } } then { NotEnoughInCorrectChoices(choices) },
        ).map { MultipleChoiceQuestion(display, choices) }
    }
}

sealed class Choice(val display: String, val explanation: String) {

    companion object {
        sealed class Error {
            object BlankDisplay : Error()
            object BlankExplanation : Error()
        }

        private fun validate(display: String, explanation: String) = Validated.applicativeNel<Error>().tupledN(
            `if` { display.isBlank() } then { Error.BlankDisplay },
            `if` { explanation.isBlank() } then { Error.BlankExplanation }
        )
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

