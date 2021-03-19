package mr.bergin.unposter.cli

import arrow.core.extensions.list.foldable.firstOption
import arrow.core.orNull
import dev.forkhandles.result4k.valueOrNull
import mr.bergin.unposter.model.*


/**
 * Playing around with the multiple choice questions
 */
fun main() {
    val user = User("Jordan")
    val question = dummyMcq()

    val choicesIndexed = question.choices.mapIndexed { index, choice -> (index + 1).toString() to choice }
    val choicesDisplay = choicesIndexed.joinToString(System.lineSeparator()) { "${it.first}) ${it.second.display}" }

    val askedQuestion = user.ask(question)

    println(
        """|${question.display}
           | 
           |$choicesDisplay
        """.trimMargin()
    )

    var response: String? = null
    while (response != "exit") {
        response = readLine()!!

        val selectedChoice = choicesIndexed.firstOption { it.first == response }
            .map { it.second }
            .map(::setOf)
            .map(::MultipleChoiceAnswer)
            .map(askedQuestion::answerWith).orNull() ?: continue

        val explanation = selectedChoice.answer.choices.first().explanation

        println("${selectedChoice.result}! $explanation")
    }
    println("Exiting")
}


private fun dummyMcq(): MultipleChoiceQuestion {
    val questionDisplay = "Which of the following is a read-only variable?"
    val choices = setOf(
        CorrectChoice("val quantity = 5", "This is a read-only Int initialized with value 5"),
        IncorrectChoice("var name = \"Hello!\"", "This is variable which can be reassigned"),
        IncorrectChoice("fun result() = true", "This is a function declaration, not a variable"),
        IncorrectChoice("class Robot", "This is a class, not a variable"),
    ).map { it.valueOrNull()!! }.toSet()
    return MultipleChoiceQuestion(questionDisplay, choices).orNull()!!
}


private fun dummyQuiz() = Quiz("Dummy Quiz", listOf(dummyMcq()))