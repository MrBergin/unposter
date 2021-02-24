package mr.bergin.unposter.model

import arrow.core.orNull

fun validMultipleChoiceQuestion() = MultipleChoiceQuestion(
    "Hello",
    setOf(Choice.CorrectChoice("Foo", "Bar"), Choice.IncorrectChoice("Baz", "Bart")).map {
        it.orNull()!!
    }.toSet()
).orNull()!!

fun validUser() = User("Foo")