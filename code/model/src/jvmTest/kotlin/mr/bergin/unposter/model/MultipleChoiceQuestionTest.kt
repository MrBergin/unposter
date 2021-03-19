package mr.bergin.unposter.model

import arrow.core.nel
import arrow.core.orNull
import dev.forkhandles.result4k.valueOrNull
import io.kotest.assertions.arrow.validation.shouldBeInvalid
import io.kotest.assertions.arrow.validation.shouldBeValid
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

class MultipleChoiceQuestionTest : StringSpec({
    "when a multiple choice question has no correct answers, then return an error" {
        val choices = setOf(IncorrectChoice("foo", "bar").valueOrNull()!!)

        val result = MultipleChoiceQuestion("Baz", choices)

        result shouldBeInvalid McqNotEnoughCorrectChoices(choices).nel()
    }

    "when a multiple choice question has no incorrect answers, then return an error" {
        val choices = setOf(CorrectChoice("foo", "bar").valueOrNull()!!)

        val result = MultipleChoiceQuestion("Baz", choices)

        result shouldBeInvalid McqNotEnoughInCorrectChoices(choices).nel()
    }

    "when no display name is provided to a multiple choice question, then return an error" {
        val choices = setOf(
            IncorrectChoice("foo", "bar").valueOrNull()!!,
            CorrectChoice("foo", "bar").valueOrNull()!!
        )

        val result = MultipleChoiceQuestion("", choices)

        result shouldBeInvalid McqDisplayIsBlank.nel()
    }

    "when enough choices and display name are provided, then return a multiple choice question" {
        val displayName = "baz"
        val choices = setOf(
            IncorrectChoice("foo", "bar").valueOrNull()!!,
            CorrectChoice("foo", "bar").valueOrNull()!!
        )

        val result = MultipleChoiceQuestion(displayName, choices)

        result shouldBeValid { (mcq) ->
            mcq.choices shouldBe choices
            mcq.display shouldBe displayName
        }
    }

    "when many things are wrong, then return many errors" {
        val result = MultipleChoiceQuestion("", setOf())

        result shouldBeInvalid {
            it.e.size shouldBeGreaterThan 1
        }
    }
})