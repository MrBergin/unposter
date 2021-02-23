package mr.bergin.unposter.model

import arrow.core.orNull
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Clock

class AnsweredQuestionTest : StringSpec({

    "when question is answered incorrectly, then result should be wrong" {
        val choices = setOf(Choice.CorrectChoice("Foo", "Bar"), Choice.IncorrectChoice("Baz", "Bart")).map {
            it.orNull()!!
        }.toSet()
        val mcq = MultipleChoiceQuestion("Hello", choices).orNull()!!
        val askedQuestion = AskedQuestion(User("Foo"), Clock.System.now(), mcq)

        val incorrectAnswer = choices.filterIsInstance<Choice.IncorrectChoice>().toSet()
        val result = askedQuestion.answer(MultipleChoiceAnswer(incorrectAnswer)).result

        result shouldBe AnswerResult.Wrong
    }

    "when question is answered correctly, then result should be wrong" {
        val choices = setOf(Choice.CorrectChoice("Foo", "Bar"), Choice.IncorrectChoice("Baz", "Bart")).map {
            it.orNull()!!
        }.toSet()
        val mcq = MultipleChoiceQuestion("Hello", choices).orNull()!!
        val askedQuestion = AskedQuestion(User("Foo"), Clock.System.now(), mcq)

        val correctAnswer = choices.filterIsInstance<Choice.CorrectChoice>().toSet()
        val result = askedQuestion.answer(MultipleChoiceAnswer(correctAnswer)).result

        result shouldBe AnswerResult.Right
    }

})