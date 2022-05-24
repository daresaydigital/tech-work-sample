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
import ir.sass.shared_domain.MovieListType
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
    fun `every element in UI must have value in TOP_RATED mode`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModelWhenItsForJustAdults()
                ))
                putInt("type",MovieListType.TOP_RATED.ordinal)
            }
        ){
            (this as MovieDetailFragment).apply {
                assertionHandlerForEveryElementInUIHasValue()
            }
        }
    }

    @Test
    fun `every element in UI must have value in FAVORITE mode`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModelWhenItsForJustAdults()
                ))
                putInt("type",MovieListType.FAVORITE.ordinal)
            }
        ){
            (this as MovieDetailFragment).apply {
                assertionHandlerForEveryElementInUIHasValue()
            }
        }
    }

    @Test
    fun `every element in UI must have value in POPLUAR mode`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModelWhenItsForJustAdults()
                ))
                putInt("type",MovieListType.POPULAR.ordinal)
            }
        ){
            (this as MovieDetailFragment).apply {
                assertionHandlerForEveryElementInUIHasValue()
            }
        }
    }


    private fun assertionHandlerForEveryElementInUIHasValue(){
        onView(withId(R.id.txt_title)).check(matches(withText("fake title")))
        onView(withId(R.id.txt_date)).check(matches(withText("fake date")))
        onView(withId(R.id.txt_lang)).check(matches(withText("En")))
        onView(withId(R.id.txt_adult)).check(matches(withText("No")))
        onView(withId(R.id.txt_vote_count)).check(matches(withText("1")))
        onView(withId(R.id.txt_overview)).check(matches(withText("fake overview")))
    }

    @Test
    fun `check the proper text is shown when adult flag is false in FAVORITE mode`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModel()
                ))
                putInt("type",MovieListType.TOP_RATED.ordinal)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.txt_adult)).check(matches(withText("Yes")))
            }
        }
    }

    @Test
    fun `check the proper text is shown when adult flag is false in POPULAR mode`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModel()
                ))
                putInt("type",MovieListType.POPULAR.ordinal)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.txt_adult)).check(matches(withText("Yes")))
            }
        }
    }

    @Test
    fun `check the proper text is shown when adult flag is false in TOP_RATED mode`(){
        launchFragmentInHiltContainer<MovieDetailFragment>(
            bundleOf().apply {
                putString("data", toJsonString(
                    getStubModel()
                ))
                putInt("type",MovieListType.TOP_RATED.ordinal)
            }
        ){
            (this as MovieDetailFragment).apply {
                onView(withId(R.id.txt_adult)).check(matches(withText("Yes")))
            }
        }
    }



    private fun getStubModelWhenItsForJustAdults() = ResultModel(
        true,"fake path", listOf(),1,
        "En","fake title","fake overview",1.0,
        "fake path","fake date","fake title",false,1.0,
        1
    )

    private fun getStubModel() = ResultModel(
        false,"fake path", listOf(),1,
        "En","fake title","fake overview",1.0,
        "fake path","fake date","fake title",false,1.0,
        1
    )
}