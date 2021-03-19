package mr.bergin.unposter.model

import arrow.core.orNull
import dev.forkhandles.result4k.valueOrNull

fun validMultipleChoiceQuestion() = MultipleChoiceQuestion(
    "Hello",
    setOf(CorrectChoice("Foo", "Bar"), IncorrectChoice("Baz", "Bart")).map {
        it.valueOrNull()!!
    }.toSet()
).orNull()!!

fun validUser() = User("Foo")