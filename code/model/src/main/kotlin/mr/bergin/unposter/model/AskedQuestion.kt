package mr.bergin.unposter.model

import kotlinx.datetime.Instant

class AskedQuestion<out Q: Question<A>, A: Answer> (
    val user: User,
    val timestamp: Instant,
    val question: Q,
) {
    fun answer(answer: A) = AnsweredQuestion(this, answer)
}

class AnsweredQuestion<A: Answer>(
    val askedQuestion: AskedQuestion<Question<A>, A>,
    val answer: A,
) {
    val result = askedQuestion.question.answer.matches(answer)
}