package mr.bergin.unposter.model

import arrow.core.orNull

fun validMultipleChoiceQuestion() = MultipleChoiceQuestion(
    "Hello",
    setOf(CorrectChoice("Foo", "Bar"), IncorrectChoice("Baz", "Bart")).map {
        it.orNull()!!
    }.toSet()
).orNull()!!

fun validUser() = User("Foo")