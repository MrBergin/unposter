package mr.bergin.unposter.model

sealed class Question<out A : Answer> {
    abstract val answer: A
}