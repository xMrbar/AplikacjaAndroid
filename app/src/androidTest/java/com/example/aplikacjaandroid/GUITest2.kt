package com.example.aplikacjaandroid

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.aplikacjaandroid.ControllerForTest
import com.example.aplikacjaandroid.ui.components.numberToMonth
import java.time.YearMonth


@RunWith(AndroidJUnit4::class)
class GUITest2 {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val controllerForTest = ControllerForTest()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHistoryAnalysis() {
        composeTestRule.setContent {controllerForTest.initNav(start = AppScreen.MainMenu)}
        composeTestRule.onNodeWithText(appContext.getString(R.string.button5Text)).performClick()
        composeTestRule.onNodeWithText(numberToMonth(YearMonth.now().monthValue!!)!!).performClick()
        composeTestRule.onNodeWithText("Maj").performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.rok)).performClick()
        composeTestRule.onNodeWithText("Maj").assertDoesNotExist()
    }

}