package sp.sample.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import sp.ax.animations.LocalTweenStyle
import sp.ax.animations.TweenStyle
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        view.setContent {
            CompositionLocalProvider(
                LocalTweenStyle provides TweenStyle(
                    duration = 500.milliseconds,
                    delay = Duration.ZERO,
                    easing = FastOutSlowInEasing,
                ),
            ) {
                MainScreen()
            }
        }
    }
}
