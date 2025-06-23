package sp.sample.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import sp.ax.animations.LocalTweenStyle
import sp.ax.animations.tweenSpec

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
            val states = remember { mutableStateOf(false) }
            val tweenSpec = tweenSpec<IntOffset>(style = LocalTweenStyle.current)
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                enter = slideInHorizontally(tweenSpec, initialOffsetX = { it }),
                exit = slideOutHorizontally(tweenSpec, targetOffsetX = { it }),
                visible = states.value
            ) {
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .wrapContentSize(),
                    text = "content",
                    style = TextStyle(color = Color.Red),
                )
            }
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        states.value = !states.value
                    }
                    .wrapContentSize(),
                text = "click",
            )
        }
    }
}
