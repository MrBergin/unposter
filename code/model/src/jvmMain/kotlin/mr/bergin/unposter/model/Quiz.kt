package mr.bergin.unposter.model

class Quiz(
    val name: String,
    val questionPool: List<Question<*>>,
)

class CompletedQuiz(
    val quiz: Quiz,
    val answers: Set<AnsweredQuestion<*>>,
)