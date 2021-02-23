package mr.bergin.unposter.model

import arrow.core.Validated
import arrow.core.extensions.applicativeNel
import arrow.core.extensions.validated.functor.map
import arrow.core.invalidNel
import arrow.core.valid
import mr.bergin.unposter.model.Choice.CorrectChoice
import mr.bergin.unposter.model.Choice.IncorrectChoice

sealed class Question<out A: Answer> {
    abstract val answer: A
}

class MultipleChoiceQuestion private constructor(
    val display: String,
    val choices: Set<Choice>,
) : Question<MultipleChoiceAnswer>() {

    override val answer = MultipleChoiceAnswer(choices.filterIsInstance<CorrectChoice>().toSet())

    companion object {
        operator fun invoke(display: String, choices: Set<Choice>) =
            Validated.applicativeNel<MultipleChoiceQuestionError>().tupledN(
                MultipleChoiceQuestionError.BlankDisplay.validate(display),
                MultipleChoiceQuestionError.NotEnoughCorrectChoices.validate(choices),
                MultipleChoiceQuestionError.NotEnoughInCorrectChoices.validate(choices),
            ).map {
                MultipleChoiceQuestion(display, choices)
            }
    }
}

sealed class MultipleChoiceQuestionError {
    object BlankDisplay : MultipleChoiceQuestionError() {
        fun validate(display: String) = if (display.isBlank()) {
            this.invalidNel()
        } else {
            display.valid()
        }
    }

    data class NotEnoughCorrectChoices(val choices: Set<Choice>) : MultipleChoiceQuestionError() {
        companion object {
            fun validate(choices: Set<Choice>) = if (choices.noneAre<CorrectChoice>()) {
                NotEnoughCorrectChoices(choices).invalidNel()
            } else {
                choices.valid()
            }
        }
    }

    data class NotEnoughInCorrectChoices(val choices: Set<Choice>) : MultipleChoiceQuestionError() {
        companion object {
            fun validate(choices: Set<Choice>) = if (choices.noneAre<IncorrectChoice>()) {
                NotEnoughInCorrectChoices(choices).invalidNel()
            } else {
                choices.valid()
            }
        }
    }
}

sealed class Choice(
    val display: String,
    val explanation: String,
) {
    class IncorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
        companion object {
            operator fun invoke(display: String, explanation: String) = Validated.applicativeNel<ChoiceError>().tupledN(
                ChoiceError.BlankDisplay.validate(display),
                ChoiceError.BlankExplanation.validate(explanation)
            ).map {
                IncorrectChoice(display, explanation)
            }
        }
    }

    class CorrectChoice private constructor(display: String, explanation: String) : Choice(display, explanation) {
        companion object {
            operator fun invoke(display: String, explanation: String) = Validated.applicativeNel<ChoiceError>().tupledN(
                ChoiceError.BlankDisplay.validate(display),
                ChoiceError.BlankExplanation.validate(explanation)
            ).map {
                CorrectChoice(display, explanation)
            }
        }
    }
}

sealed class ChoiceError {
    object BlankDisplay : ChoiceError() {
        fun validate(display: String) = if (display.isBlank()) {
            this.invalidNel()
        } else {
            display.valid()
        }
    }

    object BlankExplanation : ChoiceError() {
        fun validate(explanation: String) = if (explanation.isBlank()) {
            this.invalidNel()
        } else {
            explanation.valid()
        }
    }
}




