package com.sun.kh_mvvm_kotlin_movie.ui.main

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.sun.kh_mvvm_kotlin_movie.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun movieDetailActivityTest() {
        val relativeLayout = Espresso.onView(
            Matchers.allOf(
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.recyclerView),
                        childAtPosition(
                            ViewMatchers.withClassName(Matchers.`is`("android.widget.RelativeLayout")),
                            0
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        SystemClock.sleep(2000)
        relativeLayout.perform(ViewActions.click())

        val textView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.detail_body_trailers), ViewMatchers.withText("Trailers"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(LinearLayout::class.java),
                        1
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        SystemClock.sleep(2000)
        textView.check(ViewAssertions.matches(ViewMatchers.withText("Trailers")))

        val textView2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withText("Summary"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(LinearLayout::class.java),
                        1
                    ),
                    2
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView2.check(ViewAssertions.matches(ViewMatchers.withText("Summary")))

        val appCompatImageButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withContentDescription("Navigate up"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.movie_detail_toolbar),
                        childAtPosition(
                            ViewMatchers.withContentDescription("Aquaman"),
                            1
                        )
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        appCompatImageButton.perform(ViewActions.click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>,
        position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) &&
                        view == parent.getChildAt(position)
            }
        }
    }
}
