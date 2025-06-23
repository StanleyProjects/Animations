package sp.ax.animations

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Immutable
data class TweenStyle(
    val duration: Duration,
    val delay: Duration,
    val easing: Easing,
) {
    companion object {
        val Default = TweenStyle(
            duration = AnimationConstants.DefaultDurationMillis.milliseconds,
            delay = Duration.ZERO,
            easing = FastOutSlowInEasing,
        )
    }
}

val LocalTweenStyle = staticCompositionLocalOf { TweenStyle.Default }
