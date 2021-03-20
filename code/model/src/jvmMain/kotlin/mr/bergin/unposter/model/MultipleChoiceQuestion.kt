package mr.bergin.unposter.model

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.flatMap
import dev.forkhandles.result4k.map

sealed class Question<out A : Answer> {
    abstract val answer: A
}

class MultipleChoiceQuestion private constructor(
    val display: String,
    val choices: Set<Choice>,
) : Question<MultipleChoiceAnswer>() {

    override val answer = MultipleChoiceAnswer(choices.filterIsInstance<CorrectChoice>().toSet())

    companion object {
        operator fun invoke(display: String, choices: Set<Choice>) = McqDisplayIsBlank.validate(display).flatMap {
            McqNotEnoughCorrectChoices.validate(choices)
        }.flatMap {
            McqNotEnoughInCorrectChoices.validate(choices)
        }.map { MultipleChoiceQuestion(display, choices) }
    }
}

sealed class MultipleChoiceQuestionError

object McqDisplayIsBlank : MultipleChoiceQuestionError() {
    fun validate(display: String) = if (display.isBlank()) {
        Failure(this)
    } else {
        Success(display)
    }
}

data class McqNotEnoughCorrectChoices(val choices: Set<Choice>) : MultipleChoiceQuestionError() {
    companion object {
        fun validate(choices: Set<Choice>) = if (choices.noneAre<CorrectChoice>()) {
            Failure(McqNotEnoughCorrectChoices(choices))
        } else {
            Success(choices)
        }
    }
}

data class McqNotEnoughInCorrectChoices(val choices: Set<Choice>) : MultipleChoiceQuestionError() {
    companion object {
        fun validate(choices: Set<Choice>) = if (choices.noneAre<IncorrectChoice>()) {
            Failure(McqNotEnoughInCorrectChoices(choices))
        } else {
            Success(choices)
        }
    }
}