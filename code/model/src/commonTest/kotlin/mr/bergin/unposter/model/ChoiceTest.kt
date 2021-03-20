package mr.bergin.unposter.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import mr.bergin.result4kotest.shouldBeFailure
import mr.bergin.result4kotest.shouldBeSuccess

class ChoiceTest : StringSpec({

    "when a blank name is given to a correct choice, then return an error" {
        val result = CorrectChoice("", "explanation")

        result shouldBeFailure listOf(ChoiceDisplayIsBlank)
    }

    "when a blank name is given to an incorrect choice, then return an error" {
        val result = IncorrectChoice("", "explanation")

        result shouldBeFailure listOf(ChoiceDisplayIsBlank)
    }

    "when a blank explanation is given to a correct choice, then return an error" {
        val result = CorrectChoice("display", "")

        result shouldBeFailure listOf(ChoiceExplanationIsBlank)
    }

    "when a blank explanation is given to an incorrect choice, then return an error" {
        val result = IncorrectChoice("display", "")

        result shouldBeFailure listOf(ChoiceExplanationIsBlank)
    }

    "when a a name and explanation are provided, then return a correct choice" {
        val display = "display"
        val explanation = "explanation"

        val result = CorrectChoice(display, explanation)

        result shouldBeSuccess { (correctChoice) ->
            correctChoice.shouldBeTypeOf<CorrectChoice>()
            correctChoice.display shouldBe display
            correctChoice.explanation shouldBe explanation
        }
    }

    "when a a name and explanation are provided, then return an incorrect choice" {
        val display = "display"
        val explanation = "explanation"

        val result = IncorrectChoice(display, explanation)

        result shouldBeSuccess { (incorrectChoice) ->
            incorrectChoice.shouldBeTypeOf<IncorrectChoice>()
            incorrectChoice.display shouldBe display
            incorrectChoice.explanation shouldBe explanation
        }
    }

    "when many things are wrong with an incorrect choice, then return many errors" {
        val result = IncorrectChoice("", "")

        result shouldBeFailure {
            it.reason.size shouldBeGreaterThan 1
        }
    }

    "when many things are wrong with a correct choice, then return many errors" {
        val result = CorrectChoice("", "")

        result shouldBeFailure {
            it.reason.size shouldBeGreaterThan 1
        }
    }
})
