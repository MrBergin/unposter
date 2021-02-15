package mr.bergin.unposter.model

import arrow.core.nel
import io.kotest.assertions.arrow.validation.shouldBeInvalid
import io.kotest.assertions.arrow.validation.shouldBeValid
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import mr.bergin.unposter.model.Choice.Companion.Error.BlankDisplay
import mr.bergin.unposter.model.Choice.Companion.Error.BlankExplanation

class ChoiceTest : StringSpec({

    "when a blank name is given to a correct choice, then return an error" {
        val result = Choice.CorrectChoice("", "explanation")

        result shouldBeInvalid BlankDisplay.nel()
    }

    "when a blank name is given to an incorrect choice, then return an error" {
        val result = Choice.IncorrectChoice("", "explanation")

        result shouldBeInvalid BlankDisplay.nel()
    }

    "when a blank explanation is given to a correct choice, then return an error" {
        val result = Choice.CorrectChoice("display", "")

        result shouldBeInvalid BlankExplanation.nel()
    }

    "when a blank explanation is given to an incorrect choice, then return an error" {
        val result = Choice.IncorrectChoice("display", "")

        result shouldBeInvalid BlankExplanation.nel()
    }

    "when a a name and explanation are provided, then return a correct choice" {
        val display = "display"
        val explanation = "explanation"

        val result = Choice.CorrectChoice(display, explanation)

        result shouldBeValid { (correctChoice) ->
            correctChoice.shouldBeTypeOf<Choice.CorrectChoice>()
            correctChoice.display shouldBe display
            correctChoice.explanation shouldBe explanation
        }
    }

    "when a a name and explanation are provided, then return an incorrect choice" {
        val display = "display"
        val explanation = "explanation"

        val result = Choice.IncorrectChoice(display, explanation)

        result shouldBeValid  { (incorrectChoice) ->
            incorrectChoice.shouldBeTypeOf<Choice.IncorrectChoice>()
            incorrectChoice.display shouldBe display
            incorrectChoice.explanation shouldBe explanation
        }
    }
})
