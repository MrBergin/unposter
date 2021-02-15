package mr.bergin.unposter.model

import arrow.core.Either


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
        ): Either<Error, MultipleChoiceQuestion> {
            if (display.isBlank()) {
                return Either.left(Error.BlankDisplay)
            }
            if (choices.filterIsInstance<Choice.CorrectChoice>().isEmpty()) {
                return Either.left(Error.NotEnoughCorrectChoices(choices))
            }
            if (choices.filterIsInstance<Choice.IncorrectChoice>().isEmpty()) {
                return Either.left(Error.NotEnoughInCorrectChoices(choices))
            }
            return Either.right(MultipleChoiceQuestion(display, choices))
        }
    }
}

sealed class Choice(val display: String, val explanation: String) {

    companion object {
        sealed class Error {
            object BlankDisplay : Error()
            object BlankExplanation : Error()
        }

        private fun validate(display: String, explanation: String): Either<Error, Unit> {
            if (display.isBlank()) {
                return Either.left(Error.BlankDisplay)
            }
            if (explanation.isBlank()) {
                return Either.left(Error.BlankExplanation)
            }
            return Either.right(Unit)
        }
    }

    class IncorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
        companion object {
            operator fun invoke(display: String, explanation: String): Either<Error, IncorrectChoice> {
                return validate(display, explanation).map { IncorrectChoice(display, explanation) }
            }
        }
    }

    class CorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
        companion object {
            operator fun invoke(display: String, explanation: String): Either<Error, CorrectChoice> {
                return validate(display, explanation).map { CorrectChoice(display, explanation) }
            }
        }
    }
}

