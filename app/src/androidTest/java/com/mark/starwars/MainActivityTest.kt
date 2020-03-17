package com.mark.starwars

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.mark.starwars.ui.activity.MainActivity
import org.junit.Rule

@LargeTest
class MainActivityTest {

    @get: Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
}