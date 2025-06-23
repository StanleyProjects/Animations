package sp.ax.animations

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import kotlin.time.Duration

fun <T : Any> tweenSpec(
    duration: Duration,
    delay: Duration,
    easing: Easing,
): TweenSpec<T> {
    return tween(
        durationMillis = duration.inWholeMilliseconds.toInt(),
        delayMillis = delay.inWholeMilliseconds.toInt(),
        easing = easing,
    )
}

fun <T : Any> tweenSpec(style: TweenStyle): TweenSpec<T> {
    return tween(
        durationMillis = style.duration.inWholeMilliseconds.toInt(),
        delayMillis = style.delay.inWholeMilliseconds.toInt(),
        easing = style.easing,
    )
}
