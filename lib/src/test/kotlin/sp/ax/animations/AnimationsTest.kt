package sp.ax.animations

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.MainTestClock
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.IntSize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@RunWith(RobolectricTestRunner::class)
internal class AnimationsTest {
    @get:Rule
    val rule = createComposeRule()

    @Composable
    private fun Content(switcherTag: String, composable: @Composable (visible: Boolean) -> Unit) {
        check(switcherTag.isNotEmpty())
        Box(Modifier.fillMaxSize()) {
            val visibleState = remember { mutableStateOf(false) }
            BasicText(
                modifier = Modifier
                    .testTag(switcherTag)
                    .clickable {
                        visibleState.value = !visibleState.value
                    },
                text = switcherTag,
            )
            composable(visibleState.value)
        }
    }

    @Composable
    private fun AnimatedContent(testTag: String, text: String = testTag) {
        check(testTag.isNotEmpty())
        BasicText(
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth(),
            text = text,
        )
    }

    @Test
    fun defaultAutoAdvanceTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visible,
                    transitions = Transitions(
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(),
                    ),
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent)
            .assertIsDisplayed()
            .assertTextEquals(animatedContent)
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
    }

    @Test
    fun defaultAutoAdvanceNullableTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val text = "foobar"
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                val nullable: String? = if (visible) text else null
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    value = nullable,
                    transitions = Transitions(
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(),
                    ),
                ) { value: String ->
                    AnimatedContent(testTag = animatedContent, text = value)
                }
            }
        }
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent)
            .assertIsDisplayed()
            .assertTextEquals(text)
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
    }

    @Test
    fun defaultAutoAdvanceNullablePairTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val values = "first" to "second"
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    first = if (visible) values.first else null,
                    second = if (visible) values.second else null,
                    transitions = Transitions(
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(),
                    ),
                ) { first: String, second: String ->
                    AnimatedContent(testTag = animatedContent, text = "$first/$second")
                }
            }
        }
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent)
            .assertIsDisplayed()
            .assertTextEquals("${values.first}/${values.second}")
        rule.onNodeWithTag(switcher).performClick()
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
    }

    private fun MainTestClock.advanceTimeBy(duration: Duration, ignoreFrameDuration: Boolean = false) {
        advanceTimeBy(milliseconds = duration.inWholeMilliseconds, ignoreFrameDuration = ignoreFrameDuration)
    }

    private fun SemanticsNodeInteraction.assertOffset(
        isDisplayed: Boolean,
        onOffset: (IntSize, actual: Offset) -> Unit,
    ) {
        assertExists()
        if (isDisplayed) {
            assertIsDisplayed()
        } else {
            assertIsNotDisplayed()
        }
        val node = fetchSemanticsNode()
        val parent = node.parent ?: error("No parent!")
        onOffset(parent.size, node.positionInRoot)
    }

    @Test
    fun hFadeSlideTest() {
        val animatedContent = "animatedContent"
        val switcher = "switcher"
        val duration = AnimationConstants.DefaultDurationMillis.milliseconds
        val delay = Duration.ZERO
        val offsets = SlideStyle.Offsets.ToLeftToRight
        rule.setContent {
            Content(switcherTag = switcher) { visible: Boolean ->
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = visible,
                    transitions = Transitions.hFadeSlide(duration = duration, delay = delay, offsets = offsets),
                ) {
                    AnimatedContent(testTag = animatedContent)
                }
            }
        }
        rule.mainClock.autoAdvance = false
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
        rule.onNodeWithTag(switcher).performClick()
        rule.mainClock.advanceTimeByFrame()
        rule.onNodeWithTag(animatedContent).assertOffset(false) { size, actual ->
            assertEquals(Offset(x = size.width.toFloat(), y = 0f), actual)
        }
        rule.mainClock.advanceTimeBy(delay)
        rule.mainClock.advanceTimeBy(duration / 2)
        rule.onNodeWithTag(animatedContent).assertOffset(
            isDisplayed = false,
            onOffset = { size, actual ->
                assertTrue(actual.x > 0 && actual.x < size.width)
            },
        )
        rule.mainClock.advanceTimeBy(duration / 2)
        rule.onNodeWithTag(animatedContent).assertOffset(true) { _, actual ->
            assertEquals(Offset.Zero, actual)
        }
        rule.onNodeWithTag(animatedContent).assertTextEquals(animatedContent)
        rule.onNodeWithTag(switcher).performClick()
        rule.mainClock.advanceTimeBy(delay)
        rule.mainClock.advanceTimeBy(duration / 2)
        rule.onNodeWithTag(animatedContent).assertOffset(false) { size, actual ->
            assertTrue(actual.x > 0 && actual.x < size.width)
        }
        rule.mainClock.advanceTimeBy(duration / 2)
        rule.onNodeWithTag(animatedContent).assertOffset(false) { size, actual ->
            assertEquals(Offset(x = size.width.toFloat(), y = 0f), actual)
        }
        rule.mainClock.autoAdvance = true
        rule.onNodeWithTag(animatedContent).assertDoesNotExist()
    }
}
