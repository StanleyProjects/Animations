package sp.ax.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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

data class Transitions(
    val enter: EnterTransition,
    val exit: ExitTransition,
) {
    companion object {
        private fun fade(
            fadeInSpec: FiniteAnimationSpec<Float>,
            initialAlpha: Float,
            fadeOutSpec: FiniteAnimationSpec<Float>,
            targetAlpha: Float,
        ): Transitions {
            return Transitions(
                enter = fadeIn(fadeInSpec, initialAlpha = initialAlpha),
                exit = fadeOut(fadeOutSpec, targetAlpha = targetAlpha),
            )
        }

        @Composable
        fun fade(
            duration: Duration = LocalTweenStyle.current.duration,
            delay: Duration = LocalTweenStyle.current.delay,
            easing: Easing = LocalTweenStyle.current.easing,
            initialAlpha: Float = LocalFadeStyle.current.initialAlpha,
            targetAlpha: Float = LocalFadeStyle.current.targetAlpha,
        ): Transitions {
            return fade(
                fadeInSpec = tweenSpec(duration = duration, delay = delay, easing = easing),
                initialAlpha = initialAlpha,
                fadeOutSpec = tweenSpec(duration = duration, delay = delay, easing = easing),
                targetAlpha = targetAlpha,
            )
        }

        private fun _FadeSlide(
            fadeInSpec: FiniteAnimationSpec<Float>,
            initialAlpha: Float,
            slideInSpec: FiniteAnimationSpec<IntOffset>,
            initialOffset: (fullSize: IntSize) -> IntOffset,
            fadeOutSpec: FiniteAnimationSpec<Float>,
            targetAlpha: Float,
            slideOutSpec: FiniteAnimationSpec<IntOffset>,
            targetOffset: (fullSize: IntSize) -> IntOffset,
        ): Transitions {
            val enter = fadeIn(fadeInSpec, initialAlpha = initialAlpha) +
                slideIn(slideInSpec, initialOffset = initialOffset)
            val exit = fadeOut(fadeOutSpec, targetAlpha = targetAlpha) +
                slideOut(slideOutSpec, targetOffset = targetOffset)
            return Transitions(enter = enter, exit = exit)
        }

        @Composable
        fun hFadeSlide(
            duration: Duration = LocalTweenStyle.current.duration,
            delay: Duration = LocalTweenStyle.current.delay,
            easing: Easing = LocalTweenStyle.current.easing,
            initialAlpha: Float = LocalFadeStyle.current.initialAlpha,
            targetAlpha: Float = LocalFadeStyle.current.targetAlpha,
            offsets: SlideStyle.Offsets = LocalSlideStyle.current.horizontal,
        ): Transitions {
            return _FadeSlide(
                fadeInSpec = tweenSpec(duration = duration, delay = delay, easing = easing),
                initialAlpha = initialAlpha,
                slideInSpec = tweenSpec(duration = duration, delay = delay, easing = easing),
                initialOffset = offsets.initial,
                fadeOutSpec = tweenSpec(duration = duration, delay = delay, easing = easing),
                targetAlpha = targetAlpha,
                slideOutSpec = tweenSpec(duration = duration, delay = delay, easing = easing),
                targetOffset = offsets.target,
            )
        }

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
        ): Transitions {
            val enter = expandIn(expandInSpec, expandFrom = expandFrom, initialSize = initialSize) +
                fadeIn(fadeInSpec, initialAlpha = initialAlpha)
            val exit = fadeOut(fadeOutSpec, targetAlpha = targetAlpha) +
                shrinkOut(shrinkOutSpec, shrinkTowards = shrinkTowards, targetSize = targetSize)
            return Transitions(enter = enter, exit = exit)
        }

        @Composable
        fun vSizeFade(
            duration: Duration = LocalTweenStyle.current.duration,
            sizeDuration: Duration = duration / 2,
            easing: Easing = LocalTweenStyle.current.easing,
            initialAlpha: Float = LocalFadeStyle.current.initialAlpha,
            targetAlpha: Float = LocalFadeStyle.current.targetAlpha,
            expandFrom: Alignment = Alignment.TopCenter,
            shrinkTowards: Alignment = Alignment.TopCenter,
        ): Transitions {
            return _SizeFade(
                expandInSpec = tweenSpec(duration = sizeDuration, delay = Duration.ZERO, easing = easing),
                expandFrom = expandFrom,
                initialSize = { IntSize(it.width, 0) },
                fadeInSpec = tweenSpec(duration = duration, delay = sizeDuration, easing = easing),
                initialAlpha = initialAlpha,
                fadeOutSpec = tweenSpec(duration = duration, delay = Duration.ZERO, easing = easing),
                targetAlpha = targetAlpha,
                shrinkOutSpec = tweenSpec(duration = sizeDuration, delay = duration, easing = easing),
                shrinkTowards = shrinkTowards,
                targetSize = { IntSize(it.width, 0) },
            )
        }

        private fun _SizeFadeSlide(
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
        ): Transitions {
            val enter = expandIn(expandInSpec, expandFrom = expandFrom, initialSize = initialSize) +
                fadeIn(fadeInSpec, initialAlpha = initialAlpha) +
                slideIn(slideInSpec, initialOffset = initialOffset)
            val exit = fadeOut(fadeOutSpec, targetAlpha = targetAlpha) +
                slideOut(slideOutSpec, targetOffset = targetOffset) +
                shrinkOut(shrinkOutSpec, shrinkTowards = shrinkTowards, targetSize = targetSize)
            return Transitions(enter = enter, exit = exit)
        }

        @Composable
        fun vSizeFadeSlide(
            duration: Duration = LocalTweenStyle.current.duration,
            sizeDuration: Duration = duration / 2,
            easing: Easing = LocalTweenStyle.current.easing,
            initialAlpha: Float = LocalFadeStyle.current.initialAlpha,
            targetAlpha: Float = LocalFadeStyle.current.targetAlpha,
            expandFrom: Alignment = Alignment.TopCenter,
            shrinkTowards: Alignment = Alignment.TopCenter,
            initialOffset: (fullSize: IntSize) -> IntOffset = LocalSlideStyle.current.horizontal.initial,
            targetOffset: (fullSize: IntSize) -> IntOffset = LocalSlideStyle.current.horizontal.target,
        ): Transitions {
            return _SizeFadeSlide(
                expandInSpec = tweenSpec(duration = sizeDuration, delay = Duration.ZERO, easing = easing),
                expandFrom = expandFrom,
                initialSize = { IntSize(it.width, 0) },
                fadeInSpec = tweenSpec(duration = duration, delay = sizeDuration, easing = easing),
                initialAlpha = initialAlpha,
                slideInSpec = tweenSpec(duration = duration, delay = sizeDuration, easing = easing),
                initialOffset = initialOffset,
                fadeOutSpec = tweenSpec(duration = duration, delay = Duration.ZERO, easing = easing),
                targetAlpha = targetAlpha,
                slideOutSpec = tweenSpec(duration = duration, delay = Duration.ZERO, easing = easing),
                targetOffset = targetOffset,
                shrinkOutSpec = tweenSpec(duration = sizeDuration, delay = duration, easing = easing),
                shrinkTowards = shrinkTowards,
                targetSize = { IntSize(it.width, 0) },
            )
        }
    }
}
