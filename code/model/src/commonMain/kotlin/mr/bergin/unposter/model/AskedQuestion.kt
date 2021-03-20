package mr.bergin.unposter.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AskedQuestion<out Q: Question<A>, A: Answer> (
    val user: User,
    val at: Instant,
    val question: Q,
)

fun <Q: Question<A>, A: Answer> User.ask(
    question: Q,
    at: Instant = Clock.System.now()
) = AskedQuestion(this, at, question)
