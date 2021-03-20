package mr.bergin.unposter.model

import dev.forkhandles.result4k.valueOrNull
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import mr.bergin.result4kotest.shouldBeFailure
import mr.bergin.result4kotest.shouldBeSuccess

class MultipleChoiceQuestionTest : StringSpec({
    "when a multiple choice question has no correct answers, then return an error" {
        val choices = setOf(IncorrectChoice("foo", "bar").valueOrNull()!!)

        val result = MultipleChoiceQuestion("Baz", choices)

        result shouldBeFailure McqNotEnoughCorrectChoices(choices)
    }

    "when a multiple choice question has no incorrect answers, then return an error" {
        val choices = setOf(CorrectChoice("foo", "bar").valueOrNull()!!)

        val result = MultipleChoiceQuestion("Baz", choices)

        result shouldBeFailure McqNotEnoughInCorrectChoices(choices)
    }

    "when no display name is provided to a multiple choice question, then return an error" {
        val choices = setOf(
            IncorrectChoice("foo", "bar").valueOrNull()!!,
            CorrectChoice("foo", "bar").valueOrNull()!!
        )

        val result = MultipleChoiceQuestion("", choices)

        result shouldBeFailure McqDisplayIsBlank
    }

    "when enough choices and display name are provided, then return a multiple choice question" {
        val displayName = "baz"
        val choices = setOf(
            IncorrectChoice("foo", "bar").valueOrNull()!!,
            CorrectChoice("foo", "bar").valueOrNull()!!
        )

        val result = MultipleChoiceQuestion(displayName, choices)

        result shouldBeSuccess { (mcq) ->
            mcq.choices shouldBe choices
            mcq.display shouldBe displayName
        }
    }

    "when many things are wrong, then return an error" {
        val result = MultipleChoiceQuestion("", setOf())

        result.shouldBeFailure()
    }
})