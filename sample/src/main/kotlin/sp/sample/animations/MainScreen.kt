package sp.sample.animations

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
import androidx.compose.ui.unit.dp
import sp.ax.animations.AnimatedVisibility
import sp.ax.animations.Transitions

@Composable
internal fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {
            val firsts = remember { mutableStateOf<String?>(null) }
            val seconds = remember { mutableStateOf<String?>(null) }
            val fooState = remember { mutableStateOf(false) }
            val barState = remember { mutableStateOf(false) }
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                first = firsts.value,
                second = seconds.value,
                condition = { _, _ -> fooState.value && !barState.value },
                transitions = Transitions.vSizeFade(),
            ) { first, second ->
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(Color.Cyan)
                        .wrapContentSize(),
                    text = "[ $first | $second ]",
                    style = TextStyle(color = Color.Black),
                )
            }
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = fooState.value,
                transitions = Transitions.vSizeFadeSlide(),
            ) {
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(Color.Cyan)
                        .wrapContentSize(),
                    text = "${firsts.value}",
                    style = TextStyle(color = Color.Black),
                )
            }
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        fooState.value = !fooState.value
                    }
                    .wrapContentSize(),
                text = "foo: ${fooState.value}",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        barState.value = !barState.value
                    }
                    .wrapContentSize(),
                text = "bar: ${barState.value}",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        if (firsts.value == null) {
                            firsts.value = System.currentTimeMillis().toString()
                        } else {
                            firsts.value = null
                        }
                    }
                    .wrapContentSize(),
                text = "first: ${firsts.value}",
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        if (seconds.value == null) {
                            seconds.value = System.currentTimeMillis().hashCode().toString()
                        } else {
                            seconds.value = null
                        }
                    }
                    .wrapContentSize(),
                text = "second: ${seconds.value}",
            )
        }
    }
}
