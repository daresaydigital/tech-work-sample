package ir.sass.movie.ui.fragments.detail

import android.os.Build
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import ir.sass.base_data.model.toJsonString
import ir.sass.domain.model.ResultModel
import ir.sass.movie.ui.R
import ir.sass.movie.ui.base.launchFragmentInHiltContainer
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.P],
    application = HiltTestApplication::class,
    instrumentedPackages = [
        // required to access final members on androidx.loader.content.ModernAsyncTask
        "androidx.loader.content"
    ])
class MovieDetailFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Test
    fun `every element in UI must have value`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModelWhenItsForJustAdults()
                ))
                putBoolean("isFavorite",false)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.txt_adult)).check(matches(withText("Not suitable for kids")))
            }
        }
    }

    @Test
    fun `check the proper text is shown when adult flag is false`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModel()
                ))
                putBoolean("isFavorite",false)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.txt_title)).check(matches(withText("Title : fake title")))
                onView(withId(R.id.txt_date)).check(matches(withText("Release date : fake date")))
                onView(withId(R.id.txt_lang)).check(matches(withText("Language : En")))
                onView(withId(R.id.txt_adult)).check(matches(withText("Suitable for kids")))
                onView(withId(R.id.txt_vote_count)).check(matches(withText("Vote count : 1")))
                onView(withId(R.id.txt_vote_avg)).check(matches(withText("Vote average : 1.0")))
                onView(withId(R.id.txt_overview)).check(matches(withText("Overview : fake overview")))
            }
        }
    }

    @Test
    fun `check the button is enables at first`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModel()
                ))
                putBoolean("isFavorite",false)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.btn)).check(matches(isEnabled()))
            }
        }
    }



    @Test
    fun `the button is enabled at very first step then if we click on it it's going to be disabled`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModel()
                ))
                putBoolean("isFavorite",false)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.btn)).check(matches(isEnabled()))
                onView(withId(R.id.btn)).perform(scrollTo(),click())
                onView(withId(R.id.btn)).check(matches(not(isEnabled())))
            }
        }
    }

    fun getStubModelWhenItsForJustAdults() = ResultModel(
        true,"fake path", listOf(),1,
        "En","fake title","fake overview",1.0,
        "fake path","fake path","fake date","fake title",true,
        1.0,1
    )

    fun getStubModel() = ResultModel(
        false,"fake path", listOf(),1,
        "En","fake title","fake overview",1.0,
        "fake path","fake path","fake date","fake title",true,
        1.0,1
    )
}