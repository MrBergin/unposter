package mr.bergin.unposter.model

import arrow.core.Option
import kotlinx.datetime.Instant

class AskedQuestion(
    val user: User,
    val timestamp: Instant,
    val question: Question,
    val answer: Option<List<Choice>>,
)