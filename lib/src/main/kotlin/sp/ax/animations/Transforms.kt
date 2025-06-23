package sp.ax.animations

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntSize
import kotlin.time.Duration

object Transforms {
    fun sizeFade(
        expandInSpec: FiniteAnimationSpec<IntSize>,
        expandFrom: Alignment,
        initialSize: (fullSize: IntSize) -> IntSize,
        fadeInSpec: FiniteAnimationSpec<Float>,
        initialAlpha: Float,
        fadeOutSpec: FiniteAnimationSpec<Float>,
        targetAlpha: Float,
        shrinkOutSpec: FiniteAnimationSpec<IntSize>,
        shrinkTowards: Alignment,
        targetSize: (fullSize: IntSize) -> IntSize,
    ): ContentTransform {
        val enter = expandIn(expandInSpec, expandFrom = expandFrom, initialSize = initialSize) +
                fadeIn(fadeInSpec, initialAlpha = initialAlpha)
        val exit = fadeOut(fadeOutSpec, targetAlpha = targetAlpha) +
                shrinkOut(shrinkOutSpec, shrinkTowards = shrinkTowards, targetSize = targetSize)
        return ContentTransform(targetContentEnter = enter, initialContentExit = exit)
    }

    @Composable
    fun verticallySizeFade(
        duration: Duration = LocalTweenStyle.current.duration * 2,
        easing: Easing = LocalTweenStyle.current.easing,
        expandFrom: Alignment = Alignment.TopCenter,
        shrinkTowards: Alignment = Alignment.TopCenter,
    ): ContentTransform {
        return sizeFade(
            expandInSpec = tweenSpec(duration = duration / 2, delay = Duration.ZERO, easing = easing),
            expandFrom = expandFrom,
            initialSize = { IntSize(it.width, 0) },
            fadeInSpec = tweenSpec(duration = duration / 2, delay = duration / 2, easing = easing),
            initialAlpha = 0f,
            fadeOutSpec = tweenSpec(duration = duration / 2, delay = Duration.ZERO, easing = easing),
            targetAlpha = 0f,
            shrinkOutSpec = tweenSpec(duration = duration / 2, delay = duration / 2, easing = easing),
            shrinkTowards = shrinkTowards,
            targetSize = { IntSize(it.width, 0) },
        )
    }
}