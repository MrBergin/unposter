package mr.bergin.unposter.desktop

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.orNull
import mr.bergin.unposter.model.*

fun main() = Window {
    val user = User("Jordan")
    var answerDisplay by remember { mutableStateOf("Waiting for answer...") }
    val question = dummyMcq()
    val askedQuestion = user.ask(question)

    MaterialTheme {
        Column {
            Text(question.display)
            Text(answerDisplay)
            question.choices.forEach {
                Button(onClick = {
                    val answeredQuestion = it.run(::setOf)
                        .run(::MultipleChoiceAnswer)
                        .run(askedQuestion::answerWith)

                    val answerResult = answeredQuestion.result
                    val answerExplanation = answeredQuestion.answer.choices.first().explanation

                    answerDisplay = "$answerResult: $answerExplanation"
                }) {
                    Text(it.display)
                }
            }
        }
    }
}

private fun dummyMcq(): MultipleChoiceQuestion {
    val questionDisplay = "Which of the following is a read-only variable?"
    val choices = listOf(
        CorrectChoice("val quantity = 5", "This is a read-only Int initialized with value 5"),
        IncorrectChoice("var name = \"Hello!\"", "This is variable which can be reassigned"),
        IncorrectChoice("fun result() = true", "This is a function declaration, not a variable"),
        IncorrectChoice("class Robot", "This is a class, not a variable"),
    ).map { it.orNull()!! }.toSet()
    return MultipleChoiceQuestion(questionDisplay, choices).orNull()!!
}
