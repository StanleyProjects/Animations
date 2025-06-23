package sp.sample.animations

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sp.ax.animations.AnimatedVisibility
import sp.ax.animations.SlideStyle
import sp.ax.animations.Transitions
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun MainScreen() {
    val fooState = remember { mutableStateOf(false) }
    val barState = remember { mutableStateOf<String?>(null) }
    BackHandler {
        fooState.value = false
        barState.value = null
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(Modifier.fillMaxWidth().align(Alignment.Center)) {
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { fooState.value = true }
                    .wrapContentSize(),
                text = "foo"
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        barState.value = "time: ${System.currentTimeMillis()}"
                    }
                    .wrapContentSize(),
                text = "bar"
            )
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = fooState.value,
            transitions = Transitions.hFadeSlide(duration = 1.seconds),
        ) {
            Box(Modifier.fillMaxSize().background(Color.Red))
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            value = barState.value,
            transitions = Transitions.hFadeSlide(duration = 2.seconds, offsets = SlideStyle.Offsets.ToRightToLeft),
        ) { text ->
            Box(Modifier.fillMaxSize().background(Color.Yellow)) {
                BasicText(
                    modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                    text = text,
                    style = TextStyle(textAlign = TextAlign.Center),
                )
            }
        }
    }
}
