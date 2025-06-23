package sp.ax.animations

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

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
                    )
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
                    )
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
                    )
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
}
