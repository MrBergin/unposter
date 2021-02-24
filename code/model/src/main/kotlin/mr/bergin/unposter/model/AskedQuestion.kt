package mr.bergin.unposter.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AskedQuestion<out Q: Question<A>, A: Answer> (
    val user: User,
    val at: Instant,
    val question: Q,
) {
    fun answerWith(answer: A) = AnsweredQuestion(this, answer)
}

fun <Q: Question<A>, A: Answer> User.ask(
    question: Q,
    at: Instant = Clock.System.now()
) = AskedQuestion(this, at, question)

class AnsweredQuestion<A: Answer>(
    val askedQuestion: AskedQuestion<Question<A>, A>,
    val answer: A,
) {
    val result = askedQuestion.question.answer.matches(answer)
}