package ru.oleshchuk.module19_kotlin

import android.view.View
import android.widget.SearchView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.oleshchuk.module19_kotlin.adapter.FilmAdapter

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun recyclerViewTest1() {

        Espresso.onView(ViewMatchers.withId(R.id.films_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.films_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<FilmAdapter.FilmHolder>(
                    0,
                    ViewActions.click()))
    }

    private fun typeSearchView(text: String ) : ViewAction =
        object : ViewAction{
            override fun getDescription(): String {
                return "typeSearchView"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(ViewMatchers.isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(text, false)
            }

        }

    @Test
    fun recyclerViewTest2(){
        val testText = "abcdefg"
        Espresso.onView(ViewMatchers.withId(R.id.search)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(
            typeSearchView(testText))
    }

    @Test
    fun recyclerViewTest3(){
        Espresso.onView(ViewMatchers.withId(R.id.nav_menu_fav)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.favorites_fragment_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.nav_menu_collection)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.collection_fragment_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.nav_menu_later)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.later_fragment_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.nav_menu_home)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.home_fragment_root))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun recyclerViewTest4(){
        Espresso.onView(ViewMatchers.withId(R.id.films_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<FilmAdapter.FilmHolder>(0, ViewActions.click()))
        Espresso.onView(ViewMatchers.withId(R.id.app_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun recyclerViewTest5(){
        Espresso.onView(ViewMatchers.withId(R.id.films_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<FilmAdapter.FilmHolder>(0, ViewActions.click()))
        Espresso.onView(ViewMatchers.withId(R.id.fab_details_favourite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.fab_details_favourite)).perform(ViewActions.click())

    }

}