package mr.bergin.unposter.model

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import mr.bergin.unposter.model.Choice.Companion.Error.BlankDisplay
import mr.bergin.unposter.model.Choice.Companion.Error.BlankExplanation

class ChoiceTest : StringSpec({

    "when a blank name is given to a correct choice, then return an error" {
        val result = Choice.CorrectChoice("", "explanation")

        result shouldBeLeft BlankDisplay
    }

    "when a blank name is given to an incorrect choice, then return an error" {
        val result = Choice.IncorrectChoice("", "explanation")

        result shouldBeLeft BlankDisplay
    }

    "when a blank explanation is given to a correct choice, then return an error" {
        val result = Choice.CorrectChoice("display", "")

        result shouldBeLeft BlankExplanation
    }

    "when a blank explanation is given to an incorrect choice, then return an error" {
        val result = Choice.IncorrectChoice("display", "")

        result shouldBeLeft BlankExplanation
    }

    "when a a name and explanation are provided, then return a correct choice" {
        val display = "display"
        val explanation = "explanation"

        val result = Choice.CorrectChoice(display, explanation)

        result shouldBeRight { correctChoice ->
            correctChoice.shouldBeTypeOf<Choice.CorrectChoice>()
            correctChoice.display shouldBe display
            correctChoice.explanation shouldBe explanation
        }
    }

    "when a a name and explanation are provided, then return an incorrect choice" {
        val display = "display"
        val explanation = "explanation"

        val result = Choice.IncorrectChoice(display, explanation)

        result shouldBeRight { incorrectChoice ->
            incorrectChoice.shouldBeTypeOf<Choice.IncorrectChoice>()
            incorrectChoice.display shouldBe display
            incorrectChoice.explanation shouldBe explanation
        }
    }
})
