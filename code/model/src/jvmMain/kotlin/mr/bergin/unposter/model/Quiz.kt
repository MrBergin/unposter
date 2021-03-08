package mr.bergin.unposter.model

class Quiz(
    val name: String,
    val questionPool: List<Question<*>>,
)

fun Quiz.start(user: User, limit: Int) = InProgressQuiz(user, this, limit)

sealed class UserQuiz

class InProgressQuiz(
    val user: User,
    val quiz: Quiz,
    val limit: Int,
    val currentQuestion: Question<*> = quiz.questionPool.random(),
    val answeredQuestions: List<AnsweredQuestion<*>> = listOf(),
) : UserQuiz()

class CompletedQuiz(
    val user: User,
    val quiz: Quiz,
    val limit: Int,
    val answeredQuestions: List<AnsweredQuestion<*>> = listOf(),
) : UserQuiz()

fun InProgressQuiz.answerQuestion(
    answeredQuestion: AnsweredQuestion<*>,
): UserQuiz {
    val newAnsweredQuestions = answeredQuestions + answeredQuestion
    return if (newAnsweredQuestions.size == limit) {
        CompletedQuiz(user, quiz, limit, newAnsweredQuestions)
    } else {
        InProgressQuiz(
            user,
            quiz,
            limit,
            quiz.questionPool.random(),
            newAnsweredQuestions
            )
    }
}
