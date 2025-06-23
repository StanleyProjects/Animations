package sp.ax.animations

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Immutable
data class SlideStyle(
    val offsets: Offsets,
) {
    @Immutable
    data class Offsets(
        val initial: (fullWidth: IntSize) -> IntOffset,
        val target: (fullWidth: IntSize) -> IntOffset,
    ) {
        companion object {
            /**
             * An example of a description of how to show by moving from right to left, and hide by moving from left to right.
             *
             * ```
             * Show: [---] -> [--+] -> [-++] -> [+++]
             * Hide: [+++] -> [-++] -> [--+] -> [---]
             * ```
             */
            val ToLeftToRight = Offsets(
                initial = { IntOffset(it.width, 0) },
                target = { IntOffset(it.width, 0) },
            )

            val ToRightToLeft = Offsets(
                initial = { IntOffset(-it.width, 0) },
                target = { IntOffset(-it.width, 0) },
            )
        }
    }

    companion object {
        val Default = SlideStyle(
            offsets = Offsets.ToLeftToRight,
        )
    }
}

val LocalSlideStyle = staticCompositionLocalOf { SlideStyle.Default }
