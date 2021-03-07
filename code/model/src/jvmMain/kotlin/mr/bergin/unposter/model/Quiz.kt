package mr.bergin.unposter.model

class Quiz(
    val name: String,
    val questionPool: List<Question<*>>,
)

fun Quiz.complete(answers: Set<AnsweredQuestion<*>>) = CompletedQuiz(this, answers)

class CompletedQuiz(
    val quiz: Quiz,
    val answers: Set<AnsweredQuestion<*>>,
)