package mr.bergin.unposter.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeTypeOf

class QuizTest : StringSpec({
    "when a quiz is started, then return an InProgressQuiz" {
        val quiz = Quiz("Foo quiz", listOf(validMultipleChoiceQuestion()))

        val result = quiz.start(validUser(), 1)

        result.shouldBeTypeOf<InProgressQuiz>()
    }

    "when a quiz is with one question has one answer provided, then return a CompletedQuiz" {

        val quiz = Quiz("Foo quiz", listOf(validMultipleChoiceQuestion()))

        val startedQuiz = quiz.start(validUser(), 1)
        val askedQuestion = validUser().ask(startedQuiz.currentQuestion)
        val result = startedQuiz.answerQuestion(askedQuestion.answerWith(askedQuestion.question.answer))

        result.shouldBeTypeOf<CompletedQuiz>()
    }
})