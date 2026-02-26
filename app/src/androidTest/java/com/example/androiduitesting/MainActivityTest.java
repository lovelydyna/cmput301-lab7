package com.example.androiduitesting;

import android.content.Context;
import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = 
        new ActivityScenarioRule<>(MainActivity.class);

    // ========================================
    // MainActivity Tests (3 test cases)
    // ========================================

    /**
     * Test Case 1: Check whether the activity correctly switched
     * This test verifies that clicking on a city in the ListView 
     * correctly switches to ShowActivity
     */
    @Test
    public void testActivitySwitchOnCityClick() {
        // First, add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Toronto"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
        
        // Click on the city item in the ListView
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        
        // Verify that ShowActivity is displayed by checking for the back button
        onView(withId(R.id.backButton2)).check(matches(isDisplayed()));
        
        // Verify that the city name TextView is displayed in ShowActivity
        onView(withId(R.id.cityNameText)).check(matches(isDisplayed()));
    }

    /**
     * Test Case 2: Test whether the city name is consistent
     * This test verifies that the city name displayed in ShowActivity 
     * matches the city name that was clicked in MainActivity
     */
    @Test
    public void testCityNameConsistency() {
        String testCityName = "Vancouver";
        
        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(testCityName), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
        
        // Click on the city item
        onData(is(testCityName)).inAdapterView(withId(R.id.city_list)).perform(click());
        
        // Verify that the city name in ShowActivity matches what we added
        onView(withId(R.id.cityNameText)).check(matches(withText(testCityName)));
    }

    /**
     * Test Case 3: Test the "back" button
     * This test verifies that the back button in ShowActivity 
     * correctly returns to MainActivity
     */
    @Test
    public void testBackButtonFunctionality() {
        // First, add a city and navigate to ShowActivity
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
        
        // Click on the city to go to ShowActivity
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());
        
        // Verify we're in ShowActivity
        onView(withId(R.id.backButton2)).check(matches(isDisplayed()));
        
        // Click the back button
        onView(withId(R.id.backButton2)).perform(click());
        
        // Verify we're back in MainActivity by checking for the ListView and Add button
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }

    // ========================================
    // ShowActivity Layout Tests (3 test cases)
    // ========================================

    /**
     * Test Case 4: Test ShowActivity layout displays city name correctly
     * This test verifies that the ShowActivity layout correctly displays the passed city name
     */
    @Test
    public void testShowActivityLayoutDisplaysCityName() {
        // Create intent with city name
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ShowActivity.class);
        intent.putExtra(ShowActivity.EXTRA_CITY_NAME, "Test City");
        
        // Launch the activity with the intent
        try (ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent)) {
            // Verify that the city name TextView displays the passed city name
            onView(withId(R.id.cityNameText)).check(matches(withText("Test City")));
            
            // Verify that the back button is displayed
            onView(withId(R.id.backButton2)).check(matches(isDisplayed()));
        }
    }

    /**
     * Test Case 5: Test ShowActivity layout handles null city name gracefully
     * This test verifies that the ShowActivity layout handles the case where no city name is passed
     */
    @Test
    public void testShowActivityLayoutWithNullCityName() {
        // Create intent without city name extra
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ShowActivity.class);
        
        // Launch the activity with the intent
        try (ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent)) {
            // Verify that the city name TextView shows empty string when no city is passed
            onView(withId(R.id.cityNameText)).check(matches(withText("")));
            
            // Verify that the back button is still displayed
            onView(withId(R.id.backButton2)).check(matches(isDisplayed()));
        }
    }

    /**
     * Test Case 6: Test ShowActivity layout back button functionality
     * This test verifies that the back button in the ShowActivity layout exists and is clickable
     */
    @Test
    public void testShowActivityLayoutBackButton() {
        // Create intent with city name
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ShowActivity.class);
        intent.putExtra(ShowActivity.EXTRA_CITY_NAME, "Test City");
        
        // Launch the activity with the intent
        try (ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent)) {
            // Verify that the back button is displayed and clickable
            onView(withId(R.id.backButton2))
                .check(matches(isDisplayed()))
                .perform(click());

        }
    }
}
