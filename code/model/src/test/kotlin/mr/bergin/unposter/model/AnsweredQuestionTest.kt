package mr.bergin.unposter.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import mr.bergin.unposter.model.AnswerResult.Right
import mr.bergin.unposter.model.AnswerResult.Wrong

class AnsweredQuestionTest : StringSpec({

    "when question is answered incorrectly, then result should be wrong" {
        val askedQuestion = validUser().ask(validMultipleChoiceQuestion())
        val incorrectAnswer = MultipleChoiceAnswer(
            askedQuestion.question.choices.filterIsInstance<Choice.IncorrectChoice>().toSet()
        )

        val result = askedQuestion.answerWith(incorrectAnswer)

        result.result shouldBe Wrong
    }

    "when question is answered correctly, then result should be wrong" {
        val askedQuestion = validUser().ask(validMultipleChoiceQuestion())
        val correctAnswer = MultipleChoiceAnswer(
            askedQuestion.question.choices.filterIsInstance<Choice.CorrectChoice>().toSet()
        )

        val result = askedQuestion.answerWith(correctAnswer)

        result.result shouldBe Right
    }

})