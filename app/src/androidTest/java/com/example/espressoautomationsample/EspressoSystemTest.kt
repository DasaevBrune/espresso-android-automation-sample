package com.example.espressoautomationsample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.not

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoSystemTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun login_success_navigates_to_items() {
        // Enter valid credentials
        onView(withId(R.id.etUsername)).perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("password"), closeSoftKeyboard())
        
        // Click Sign In
        onView(withId(R.id.btnSignIn)).perform(click())

        // Verify ItemsActivity is displayed (header "Items" visible)
        onView(withId(R.id.tvHeader)).check(matches(isDisplayed()))
        onView(withId(R.id.tvHeader)).check(matches(withText("Items")))
    }

    @Test
    fun login_invalid_shows_error_and_stays_on_login() {
        // Enter invalid credentials
        onView(withId(R.id.etUsername)).perform(typeText("wrong"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("wrong"), closeSoftKeyboard())

        // Click Sign In
        onView(withId(R.id.btnSignIn)).perform(click())

        // Verify "Invalid credentials" visible
        onView(withId(R.id.tvError)).check(matches(isDisplayed()))
        onView(withId(R.id.tvError)).check(matches(withText("Invalid credentials")))
        
        // Verify still on LoginActivity (sign in button still visible)
        onView(withId(R.id.btnSignIn)).check(matches(isDisplayed()))
    }

    @Test
    fun click_item_opens_detail_with_correct_title() {
        // Login first
        loginAsDemo()

        // Click "Item 5"
        onView(withId(R.id.rvItems))
            .perform(RecyclerViewActions.actionOnItem<ItemsAdapter.ViewHolder>(
                hasDescendant(withText("Item 5")), click()
            ))

        // Verify DetailActivity title == "Item 5"
        onView(withId(R.id.tvTitle)).check(matches(withText("Item 5")))
    }

    @Test
    fun scroll_and_open_item_15() {
        // Login first
        loginAsDemo()

        // Scroll RecyclerView to "Item 15", click it
        onView(withId(R.id.rvItems))
            .perform(RecyclerViewActions.scrollTo<ItemsAdapter.ViewHolder>(
                hasDescendant(withText("Item 15"))
            ))
            .perform(RecyclerViewActions.actionOnItem<ItemsAdapter.ViewHolder>(
                hasDescendant(withText("Item 15")), click()
            ))

        // Verify detail title == "Item 15"
        onView(withId(R.id.tvTitle)).check(matches(withText("Item 15")))
    }

    @Test
    fun favorite_persists_for_item() {
        // Login first
        loginAsDemo()

        // Open "Item 3"
        onView(withId(R.id.rvItems))
            .perform(RecyclerViewActions.actionOnItem<ItemsAdapter.ViewHolder>(
                hasDescendant(withText("Item 3")), click()
            ))

        // Toggle Favorite on, add note "test note", Save
        onView(withId(R.id.cbFavorite)).perform(click())
        onView(withId(R.id.etNotes)).perform(typeText("test note"), closeSoftKeyboard())
        onView(withId(R.id.btnSave)).perform(click())

        // Navigate back to list is automatic on Save in our app implementation (finish())
        // but let's verify we are back at list
        onView(withId(R.id.tvHeader)).check(matches(isDisplayed()))

        // Reopen "Item 3"
        onView(withId(R.id.rvItems))
            .perform(RecyclerViewActions.actionOnItem<ItemsAdapter.ViewHolder>(
                hasDescendant(withText("Item 3")), click()
            ))

        // Verify Favorite is still checked and Notes contains "test note"
        onView(withId(R.id.cbFavorite)).check(matches(isChecked()))
        onView(withId(R.id.etNotes)).check(matches(withText("test note")))
    }

    private fun loginAsDemo() {
        onView(withId(R.id.etUsername)).perform(typeText("demo"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.btnSignIn)).perform(click())
    }
}
