package com.app.shubhamjhunjhunwala.thebakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

/**
 * Created by shubham on 22/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainUITest {
    public String NAME = "Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<DetailsActivity> detailsActivityTestRule;

    @Before
    public void init() {
        mainActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void allTests() {
        onView(ViewMatchers.withId(R.id.dishes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.dishes_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.ingredients_list_view)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.steps_list_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.video_view)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.next_button)).perform(click());
        onView(withId(R.id.video_view)).check(matches(not(isDisplayed())));
    }
}
