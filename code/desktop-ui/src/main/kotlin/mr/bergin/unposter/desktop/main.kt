package mr.bergin.unposter.desktop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.orNull
import mr.bergin.unposter.model.*

@ExperimentalAnimationApi
fun main() = Window {
    val user = User("Jordan")
    var answerDisplay by remember { mutableStateOf("") }
    val question = dummyQuestions()
    val askedQuestion = user.ask(question)

    @Composable
    fun Choice.toButton() = Button(onClick = {
        val answeredQuestion = run(::setOf)
            .run(::MultipleChoiceAnswer)
            .run(askedQuestion::answerWith)

        answerDisplay = "${answeredQuestion.result}: $explanation"
    }) {
        Text(display)
    }

    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(question.display)
            Spacer(Modifier.size(20.dp))
            Row (verticalAlignment = Alignment.CenterVertically) {
                question.choices.forEach {
                    Spacer(Modifier.size(20.dp))
                    it.toButton()
                }
                Spacer(Modifier.size(20.dp))
            }
            Spacer(Modifier.size(20.dp))
            AnimatedVisibility(answerDisplay.isNotBlank()) {
                Text(answerDisplay)
            }
        }
    }
}


private fun dummyQuestions(): MultipleChoiceQuestion {
    val questionDisplay = "Which of the following is a read-only variable?"
    val choices = listOf(
        CorrectChoice("val quantity = 5", "This is a read-only Int initialized with value 5"),
        IncorrectChoice("var name = \"Hello!\"", "This is variable which can be reassigned"),
        IncorrectChoice("fun result() = true", "This is a function declaration, not a variable"),
        IncorrectChoice("class Robot", "This is a class, not a variable"),
    ).map { it.orNull()!! }.toSet()
    return MultipleChoiceQuestion(questionDisplay, choices).orNull()!!
}