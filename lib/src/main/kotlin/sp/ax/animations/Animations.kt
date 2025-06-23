package sp.ax.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> AnimatedVisibility(
    modifier: Modifier,
    value: T?,
    condition: (T) -> Boolean = { true },
    transform: ContentTransform,
    label: String = "AnimatedVisibility",
    content: @Composable AnimatedVisibilityScope.(T) -> Unit
) {
    val states = remember { mutableStateOf(value) }
    LaunchedEffect(value) {
        if (value != null) states.value = value
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = value != null && condition(value),
        enter = transform.targetContentEnter,
        exit = transform.initialContentExit,
        label = label,
    ) {
        val actual = states.value
        if (actual != null) content(actual)
    }
}

@Composable
fun <T : Any, U : Any> AnimatedVisibility(
    modifier: Modifier,
    first: T?,
    second: U?,
    condition: (T, U) -> Boolean = { _, _ -> true },
    transform: ContentTransform,
    label: String = "AnimatedVisibility",
    content: @Composable AnimatedVisibilityScope.(T, U) -> Unit
) {
    val firsts = remember { mutableStateOf(first) }
    val seconds = remember { mutableStateOf(second) }
    LaunchedEffect(first, second) {
        if (first != null) firsts.value = first
        if (second != null) seconds.value = second
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = first != null && second != null && condition(first, second),
        enter = transform.targetContentEnter,
        exit = transform.initialContentExit,
        label = label,
    ) {
        val f = firsts.value
        val s = seconds.value
        if (f != null && s != null) content(f, s)
    }
}
