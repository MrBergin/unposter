package mr.bergin.unposter.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import mr.bergin.unposter.model.AnswerResult.Right
import mr.bergin.unposter.model.AnswerResult.Wrong

class AnsweredQuestionTest : StringSpec({
    "when question is answered incorrectly, then result should be wrong" {
        val askedQuestion = validUser().ask(validMultipleChoiceQuestion())
        val incorrectAnswer = MultipleChoiceAnswer(
            askedQuestion.question.choices.filterIsInstance<IncorrectChoice>().toSet()
        )

        val answeredQuestion = askedQuestion.answerWith(incorrectAnswer)

        answeredQuestion.result shouldBe Wrong
    }

    "when question is answered correctly, then result should be wrong" {
        val askedQuestion = validUser().ask(validMultipleChoiceQuestion())
        val correctAnswer = MultipleChoiceAnswer(
            askedQuestion.question.choices.filterIsInstance<CorrectChoice>().toSet()
        )

        val answeredQuestion = askedQuestion.answerWith(correctAnswer)

        answeredQuestion.result shouldBe Right
    }

})