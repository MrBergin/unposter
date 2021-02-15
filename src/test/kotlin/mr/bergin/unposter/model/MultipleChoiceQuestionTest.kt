package mr.bergin.unposter.model

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import mr.bergin.unposter.model.MultipleChoiceQuestion.Companion.Error.*

class MultipleChoiceQuestionTest : StringSpec({
    "when a multiple choice question has no correct answers, then return an error" {
        val choices = listOf(Choice.IncorrectChoice("foo", "bar").orNull()!!)

        val result = MultipleChoiceQuestion("Baz", choices)

        result shouldBeLeft NotEnoughCorrectChoices(choices)
    }

    "when a multiple choice question has no incorrect answers, then return an error" {
        val choices = listOf(Choice.CorrectChoice("foo", "bar").orNull()!!)

        val result = MultipleChoiceQuestion("Baz", choices)

        result shouldBeLeft NotEnoughInCorrectChoices(choices)
    }

    "when no display name is provided to a multiple choice question, then return an error" {
        val choices = listOf(
            Choice.IncorrectChoice("foo", "bar").orNull()!!,
            Choice.CorrectChoice("foo", "bar").orNull()!!
        )

        val result = MultipleChoiceQuestion("", choices)

        result shouldBeLeft BlankDisplay
    }

    "when enough choices and display name are provided, then return a multiple choice question" {
        val displayName = "baz"
        val choices = listOf(
            Choice.IncorrectChoice("foo", "bar").orNull()!!,
            Choice.CorrectChoice("foo", "bar").orNull()!!
        )

        val result = MultipleChoiceQuestion(displayName, choices)

        result shouldBeRight { mcq ->
            mcq.choices shouldBe choices
            mcq.display shouldBe displayName
        }
    }
})