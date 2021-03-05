package mr.bergin.unposter.model

class AnsweredQuestion<A: Answer>(
    val askedQuestion: AskedQuestion<Question<A>, A>,
    val answer: A,
) {
    val result = askedQuestion.question.answer.matches(answer)
}

fun <A: Answer> AskedQuestion<*, A>.answerWith(answer: A) = AnsweredQuestion(this, answer)