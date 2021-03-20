package mr.bergin.unposter.model

import dev.forkhandles.result4k.valueOrNull

fun validMultipleChoiceQuestion() = MultipleChoiceQuestion(
    "Hello",
    setOf(CorrectChoice("Foo", "Bar"), IncorrectChoice("Baz", "Bart")).map {
        it.valueOrNull()!!
    }.toSet()
).valueOrNull()!!

fun validUser() = User("Foo")