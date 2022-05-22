package ir.sass.movie.ui.fragments.mainList

import android.content.Context
import android.os.Build
import androidx.core.os.bundleOf
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import ir.sass.movie.ui.base.launchFragmentInHiltContainer
import ir.sass.movie.ui.base.withRecyclerView
import ir.sass.movie.ui.R
import ir.sass.shared_data.db.AppDatabase
import ir.sass.shared_data.db.MovieDao
import ir.sass.shared_data.db.model.ResultEntity
import org.junit.After
import org.junit.Before
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
class MovieListFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)




/*    actually if the items have value in list then we can be sure the list has item too
    so we don't need two write two test for that*/
    @Test
    fun `in online mode test if all text in list has values and make sure the list has items`(){
        launchFragmentInHiltContainer<MovieListFragment>(
            bundleOf().apply {
                putBoolean("isFavorite",false)
            }
        ){
            (this as MovieListFragment).apply {
                Espresso.onView(
                    withRecyclerView(R.id.recyclerview)
                        .atPositionOnView(0, R.id.txt_title)
                )
                    .check(ViewAssertions.matches(ViewMatchers.withText("Title : fake title")))

                Espresso.onView(
                    withRecyclerView(R.id.recyclerview)
                        .atPositionOnView(0, R.id.txt_date)
                )
                    .check(ViewAssertions.matches(ViewMatchers.withText("Release date : fake date")))
            }
        }
    }

    @Test
    fun `in offline mode test if all text in list has values and make sure the list has items`(){
        launchFragmentInHiltContainer<MovieListFragment>(
            bundleOf().apply {
                putBoolean("isFavorite",true)
            }
        ){
            (this as MovieListFragment).apply {
                onView(
                    withRecyclerView(R.id.recyclerview)
                        .atPositionOnView(0, R.id.txt_title)
                )
                    .check(ViewAssertions.matches(ViewMatchers.withText("Title : fake db title")))

                onView(
                    withRecyclerView(R.id.recyclerview)
                        .atPositionOnView(0, R.id.txt_date)
                )
                    .check(ViewAssertions.matches(ViewMatchers.withText("Release date : fake db date")))
            }
        }
    }

}