package mr.bergin.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.forkhandles.result4k.valueOrNull
import mr.bergin.unposter.model.*

@ExperimentalAnimationApi
@Composable
fun App() {
    val user = User("Jordan")
    var answerDisplay by remember { mutableStateOf("") }
    val question = dummyQuestions()
    val askedQuestion = user.ask(question)

    @Composable
    fun Choice.toButton() = Button(onClick = {
        val answeredQuestion = askedQuestion.answerWith(MultipleChoiceAnswer(setOf()))

        answerDisplay = "${answeredQuestion.result}: $explanation"
    }) {
        Text(display)
    }

    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(question.display)
                Spacer(Modifier.size(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
}

private fun dummyQuestions(): MultipleChoiceQuestion {
    val questionDisplay = "Which of the following is a read-only variable?"
    val choices = setOf(
        CorrectChoice("val quantity = 5", "This is a read-only Int initialized with value 5"),
        IncorrectChoice("var name = \"Hello!\"", "This is variable which can be reassigned"),
        IncorrectChoice("fun result() = true", "This is a function declaration, not a variable"),
        IncorrectChoice("class Robot", "This is a class, not a variable"),
    ).map { it.valueOrNull()!! }.toSet()
    return MultipleChoiceQuestion(questionDisplay, choices).valueOrNull()!!
}