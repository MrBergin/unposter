package mr.bergin.unposter.model

enum class AnswerResult {
    Right,
    Wrong,
}

sealed class Answer {
    abstract fun matches(other: Answer): AnswerResult
}

data class MultipleChoiceAnswer(
    val choices: Set<Choice>,
) : Answer() {
    override fun matches(other: Answer): AnswerResult {
        return if (this == other) AnswerResult.Right else AnswerResult.Wrong
    }
}