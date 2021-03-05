package mr.bergin.unposter.model

import arrow.core.Validated
import arrow.core.extensions.applicativeNel
import arrow.core.extensions.validated.functor.map
import arrow.core.invalidNel
import arrow.core.valid

sealed class Question<out A : Answer> {
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
                McqDisplayIsBlank.validate(display),
                McqNotEnoughCorrectChoices.validate(choices),
                McqNotEnoughInCorrectChoices.validate(choices),
            ).map {
                MultipleChoiceQuestion(display, choices)
            }
    }
}

sealed class MultipleChoiceQuestionError

object McqDisplayIsBlank : MultipleChoiceQuestionError() {
    fun validate(display: String) = if (display.isBlank()) {
        this.invalidNel()
    } else {
        display.valid()
    }
}

data class McqNotEnoughCorrectChoices(val choices: Set<Choice>) : MultipleChoiceQuestionError() {
    companion object {
        fun validate(choices: Set<Choice>) = if (choices.noneAre<CorrectChoice>()) {
            McqNotEnoughCorrectChoices(choices).invalidNel()
        } else {
            choices.valid()
        }
    }
}

data class McqNotEnoughInCorrectChoices(val choices: Set<Choice>) : MultipleChoiceQuestionError() {
    companion object {
        fun validate(choices: Set<Choice>) = if (choices.noneAre<IncorrectChoice>()) {
            McqNotEnoughInCorrectChoices(choices).invalidNel()
        } else {
            choices.valid()
        }
    }
}