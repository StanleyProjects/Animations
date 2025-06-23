package sp.ax.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> AnimatedVisibility(
    modifier: Modifier,
    value: T?,
    enter: EnterTransition,
    exit: ExitTransition,
    label: String = "AnimatedVisibility",
    content: @Composable AnimatedVisibilityScope.(T) -> Unit
) {
    val states = remember { mutableStateOf(value) }
    LaunchedEffect(value) {
        if (value != null) states.value = value
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = value != null,
        enter = enter,
        exit = exit,
        label = label,
    ) {
        val actual = states.value
        if (actual != null) content(actual)
    }
}
