package sp.ax.animations

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.time.Duration

object Transforms {
    private fun _SizeFade(
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
    fun vSizeFade(
        duration: Duration = LocalTweenStyle.current.duration * 2,
        easing: Easing = LocalTweenStyle.current.easing,
        expandFrom: Alignment = Alignment.TopCenter,
        shrinkTowards: Alignment = Alignment.TopCenter,
    ): ContentTransform {
        return _SizeFade(
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

    private fun _SizeFade_Slide(
        expandInSpec: FiniteAnimationSpec<IntSize>,
        expandFrom: Alignment,
        initialSize: (fullSize: IntSize) -> IntSize,
        fadeInSpec: FiniteAnimationSpec<Float>,
        initialAlpha: Float,
        slideInSpec: FiniteAnimationSpec<IntOffset>,
        initialOffset: (fullSize: IntSize) -> IntOffset,
        fadeOutSpec: FiniteAnimationSpec<Float>,
        targetAlpha: Float,
        slideOutSpec: FiniteAnimationSpec<IntOffset>,
        targetOffset: (fullSize: IntSize) -> IntOffset,
        shrinkOutSpec: FiniteAnimationSpec<IntSize>,
        shrinkTowards: Alignment,
        targetSize: (fullSize: IntSize) -> IntSize,
    ): ContentTransform {
        val enter = expandIn(expandInSpec, expandFrom = expandFrom, initialSize = initialSize) +
                fadeIn(fadeInSpec, initialAlpha = initialAlpha) +
                slideIn(slideInSpec, initialOffset = initialOffset)
        val exit = fadeOut(fadeOutSpec, targetAlpha = targetAlpha) +
                slideOut(slideOutSpec, targetOffset = targetOffset) +
                shrinkOut(shrinkOutSpec, shrinkTowards = shrinkTowards, targetSize = targetSize)
        return ContentTransform(targetContentEnter = enter, initialContentExit = exit)
    }

    @Composable
    fun vSizeFadeSlide(
        duration: Duration = LocalTweenStyle.current.duration * 2,
        easing: Easing = LocalTweenStyle.current.easing,
        expandFrom: Alignment = Alignment.TopCenter,
        shrinkTowards: Alignment = Alignment.TopCenter,
        initialOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(it.width, 0) },
        targetOffset: (fullSize: IntSize) -> IntOffset = { IntOffset(it.width, 0) },
    ): ContentTransform {
        return _SizeFade_Slide(
            expandInSpec = tweenSpec(duration = duration / 2, delay = Duration.ZERO, easing = easing),
            expandFrom = expandFrom,
            initialSize = { IntSize(it.width, 0) },
            fadeInSpec = tweenSpec(duration = duration / 2, delay = duration / 2, easing = easing),
            initialAlpha = 0f,
            slideInSpec = tweenSpec(duration = duration / 2, delay = duration / 2, easing = easing),
            initialOffset = initialOffset,
            fadeOutSpec = tweenSpec(duration = duration / 2, delay = Duration.ZERO, easing = easing),
            targetAlpha = 0f,
            slideOutSpec = tweenSpec(duration = duration / 2, delay = Duration.ZERO, easing = easing),
            targetOffset = targetOffset,
            shrinkOutSpec = tweenSpec(duration = duration / 2, delay = duration / 2, easing = easing),
            shrinkTowards = shrinkTowards,
            targetSize = { IntSize(it.width, 0) },
        )
    }
}