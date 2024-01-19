package com.example.aplikacjaandroid


import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.aplikacjaandroid.ui.screens.AccountScreen
import com.example.aplikacjaandroid.ui.screens.AddNewExpensePlanScreen
import com.example.aplikacjaandroid.ui.screens.AddNewExpenseScreen
import com.example.aplikacjaandroid.ui.screens.AddNewOutPlanScreen
import com.example.aplikacjaandroid.ui.screens.AddNewRevenuePlanScreen
import com.example.aplikacjaandroid.ui.screens.AddNewRevenueScreen
import com.example.aplikacjaandroid.ui.screens.ChangePasswordScreen
import com.example.aplikacjaandroid.ui.screens.CreateAccountScreen
import com.example.aplikacjaandroid.ui.screens.CreateFile
import com.example.aplikacjaandroid.ui.screens.ExpensesPlanScreen
import com.example.aplikacjaandroid.ui.screens.ExpensesScreen
import com.example.aplikacjaandroid.ui.screens.HistoryAnalysisScreen
import com.example.aplikacjaandroid.ui.screens.MainMenuScreen
import com.example.aplikacjaandroid.ui.screens.RevenuesPlanScreen
import com.example.aplikacjaandroid.ui.screens.RevenuesScreen
import com.example.aplikacjaandroid.ui.screens.SignInScreen
import com.example.aplikacjaandroid.ui.screens.StatisticsScreen
import com.example.aplikacjaandroid.ui.screens.UserAccountScreen
import com.example.aplikacjaandroid.ui.screens.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class ControllerForTest{
    @Composable
    fun initNav(navController: NavHostController = rememberNavController(),
                start: AppScreen){
        NavHost(
            navController = navController,
            startDestination = start.name,
            modifier = Modifier
        ) {
            composable( route = AppScreen.Welcome.name){
                WelcomeScreen(modifier = Modifier.fillMaxSize(),
                    onSignInButtonClickedHandler = {navController.navigate(AppScreen.SignIn.name)},
                    onCreateAccountButtonClickedHandler = {navController.navigate(AppScreen.CreateAccount.name)}
                )
            }

            composable( route = AppScreen.SignIn.name){
                SignInScreen(modifier = Modifier.fillMaxSize(),
                    onSignInButtonClickedHandler = {navController.navigate(AppScreen.MainMenu.name)}
                )
            }

            composable( route = AppScreen.CreateAccount.name){
                CreateAccountScreen(modifier = Modifier.fillMaxSize(),
                    onCreateAccountButtonClickedHandler = {navController.navigate((AppScreen.MainMenu.name))}
                )
            }

            composable( route = AppScreen.MainMenu.name){
                CreateFile(LocalContext.current)
                MainMenuScreen(modifier = Modifier.fillMaxSize(),
                    onAccountBalanceButtonClickedHandler = {navController.navigate((AppScreen.AccountBalance.name))},
                    onBudgetPlanButtonClickedHandler = {navController.navigate((AppScreen.RevenuesPlan.name))},
                    onStatisticsButtonClickedHandler = {navController.navigate((AppScreen.Statistics.name))},
                    onHistoryAnalysisButtonClickedHandler = {navController.navigate((AppScreen.HistoryAnalysis.name))},
                    onUserAccountButtonClickedHandler = {navController.navigate((AppScreen.UserAccount.name))},
                    onLogOutButtonClickedHandler = { signOut(navController) }
                )
            }

            composable( route = AppScreen.Statistics.name){
                StatisticsScreen(modifier = Modifier.fillMaxSize())
            }

            composable( route = AppScreen.UserAccount.name){
                UserAccountScreen(
                    modifier = Modifier.fillMaxSize(),
                    onChangePasswordButtonClickedHandler = {navController.navigate((AppScreen.ChangePassword.name))},
                    onDeleteAccountButtonClickedHandler =  {navigateToWelcome(navController)}
                )
            }

            composable( route = AppScreen.ChangePassword.name){
                ChangePasswordScreen(modifier = Modifier.fillMaxSize())
            }

            composable( route = AppScreen.AccountBalance.name){
                AccountScreen(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                    onRevenuesButtonClickedHandler = { navigateDirectTo(navController, AppScreen.Revenues) },
                    onExpensesButtonClickedHandler = { navigateDirectTo(navController, AppScreen.Expenses) },
                    onAddNewOutPlanButtonClickedHandler = { navController.navigate((AppScreen.AddNewOutPlan.name))}
                )
            }

            composable( route = AppScreen.Revenues.name) {
                RevenuesScreen(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                    onAccountBalanceButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AccountBalance) },
                    onExpensesButtonClickedHandler = { navigateDirectTo(navController, AppScreen.Expenses)},
                    onAddNewRevenueButtonClickedHandler = { navController.navigate((AppScreen.AddNewRevenue.name))}
                )
            }

            composable( route = AppScreen.Expenses.name) {
                ExpensesScreen(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                    onAccountBalanceButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AccountBalance)},
                    onRevenuesButtonClickedHandler = { navigateDirectTo(navController, AppScreen.Revenues)},
                    onAddNewExpenseButtonClickedHandler = { navController.navigate((AppScreen.AddNewExpense.name)) }
                )
            }

            composable( route = AppScreen.RevenuesPlan.name){
                RevenuesPlanScreen(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                    onExpensesPlanButtonClickedHandler = { navigateDirectTo(navController, AppScreen.ExpensesPlan) },
                    onAddRevenuesPlanAddButtonClieckedHandler = { navController.navigate((AppScreen.AddNewRevenuePlan.name)) })
            }

            composable( route = AppScreen.ExpensesPlan.name){
                ExpensesPlanScreen(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                    onRevenuesPlanButtonClickedHandler = { navigateDirectTo(navController, AppScreen.RevenuesPlan) },
                    onAddExpensesPlanAddButtonClieckedHandler = { navController.navigate((AppScreen.AddNewExpensePlan.name)) })
            }

            composable( route = AppScreen.AddNewExpensePlan.name){
                AddNewExpensePlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenuePlan) },
                    onClickGoBack = { navController.popBackStack()
                        navigateDirectTo(navController, AppScreen.ExpensesPlan)})
            }

            composable( route = AppScreen.AddNewRevenuePlan.name){
                AddNewRevenuePlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onExpenseAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpensePlan) },
                    onClickGoBack = { navController.popBackStack()
                        navigateDirectTo(navController, AppScreen.RevenuesPlan)})
            }

            composable( route = AppScreen.AddNewExpense.name){
                AddNewExpenseScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenue) },
                    onOutPlanAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewOutPlan) },
                    onClickGoBack = { navController.popBackStack()
                        navigateDirectTo(navController, AppScreen.Expenses)})
            }

            composable( route = AppScreen.AddNewRevenue.name){
                AddNewRevenueScreen(modifier = Modifier
                    .fillMaxSize(),
                    onExpensesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpense)  },
                    onOutPlanAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewOutPlan) },
                    onClickGoBack = { navController.popBackStack()
                        navigateDirectTo(navController, AppScreen.Revenues)})
            }

            composable( route = AppScreen.AddNewOutPlan.name){
                AddNewOutPlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenue) },
                    onExpensesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpense) },
                    onClickGoBack = { navController.popBackStack()
                        navigateDirectTo(navController, AppScreen.AccountBalance)})
            }

            //---------------------------------------------------------------------------
            // Not decoupled yet, still activities

            composable( route = AppScreen.HistoryAnalysis.name){
                HistoryAnalysisScreen(modifier = Modifier.fillMaxSize())
            }

        }
    }

    // pops up BackStack until it reaches Welcome destination
    private fun navigateToWelcome(navController: NavController){
        navController.popBackStack(AppScreen.Welcome.name, inclusive = false)
    }

    // signs current user out and pops up BackStack until it reaches Welcome destination
    private fun signOut(navController: NavController){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        auth.signOut()
        if (user != null) {
            Log.d("FirebaseAuth", "User ${user.email} signed out")
            navController.popBackStack(AppScreen.Welcome.name, inclusive = false)
        }
        else{
            Log.d("FirebaseAuth", "Unable to sign out current user")
        }

    }

    // pops the stack and navigates to destination of given name
    private fun navigateDirectTo(navController: NavController, appScreen: AppScreen){
        navController.popBackStack()
        navController.navigate(appScreen.name)
    }
}

@RunWith(AndroidJUnit4::class)
class GUITest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val controllerForTest = ControllerForTest()

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setMenu() {
        composeTestRule.setContent {
            controllerForTest.initNav(start = AppScreen.MainMenu)
        }
    }

    private fun setDefault() {
        composeTestRule.setContent {
            controllerForTest.initNav(start = AppScreen.Welcome)
        }
    }

    @Test
    fun zaloguj() {
        setDefault()
        composeTestRule.onNodeWithText("ZALOGUJ").performClick()
        composeTestRule.onNodeWithText("Adres email").performTextInput("bartosz.pyzio@op.pl")
        composeTestRule.onNodeWithText("Hasło").performTextInput("Test1234!")
        composeTestRule.onNodeWithText("ZALOGUJ").performClick()
        Thread.sleep(2500)
    }

    @Test
    fun wyloguj() {
        zaloguj()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wyloguj)).performClick()
        composeTestRule.onNodeWithText("ZALOGUJ").assertExists()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun testButtonClick() {
        setMenu()
        composeTestRule.onNodeWithText(appContext.getString(R.string.button1Text)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.accountBalance)).assertExists()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun testAddExpense(): Unit {
        zaloguj()
        val name: String = "GUI TEST"
        composeTestRule.onNodeWithText(appContext.getString(R.string.button1Text)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wydatki)).performClick()

        composeTestRule.onNodeWithText(appContext.getString(R.string.dodaj)).performClick()
        composeTestRule.onNodeWithText("Tytuł").performClick().performTextInput(name)

        composeTestRule.onNodeWithText("Kwota").performClick().performTextInput("9.99")

        composeTestRule.onNodeWithText("Kategoria").performClick()
        composeTestRule.onNodeWithText("TV").performClick()

        composeTestRule.onNodeWithTag("A").performClick()
        //composeTestRule.onAllNodes(isRoot()).get(1).printToLog("T:")
        composeTestRule.onNodeWithText("Wednesday, January 10, 2024").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithTag("B").performClick()
        composeTestRule.onNodeWithText(name).assertExists()
    }

    @Test
    fun testDeleteExpense() {
        zaloguj()
        val name: String = "GUI TEST USUŃ"
        composeTestRule.onNodeWithText(appContext.getString(R.string.button1Text)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wydatki)).performClick()

        composeTestRule.onNodeWithText(appContext.getString(R.string.dodaj)).performClick()
        composeTestRule.onNodeWithText("Tytuł").performClick().performTextInput(name)

        composeTestRule.onNodeWithText("Kwota").performClick().performTextInput("9.99")

        composeTestRule.onNodeWithText("Kategoria").performClick()
        composeTestRule.onNodeWithText("TV").performClick()

        composeTestRule.onNodeWithTag("A").performClick()
        //composeTestRule.onAllNodes(isRoot()).get(1).printToLog("T:")
        composeTestRule.onNodeWithText("Wednesday, January 10, 2024").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithTag("B").performClick()

        composeTestRule.onNodeWithText(name).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.usun)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.przychody)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wydatki)).performClick()
        composeTestRule.onNodeWithText(name).assertDoesNotExist()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun testAddExpensePlan(): Unit {
        zaloguj()
        val name: String = "GUI TEST"
        composeTestRule.onNodeWithText(appContext.getString(R.string.button2Text)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wydatki)).performClick()

        composeTestRule.onNodeWithText(appContext.getString(R.string.dodaj)).performClick()
        composeTestRule.onNodeWithText("Tytuł").performClick().performTextInput(name)

        composeTestRule.onNodeWithText("Kwota").performClick().performTextInput("19.99")

        composeTestRule.onNodeWithText("Kategoria").performClick()
        composeTestRule.onNodeWithText("TV").performClick()

        composeTestRule.onNodeWithText("Częstość płatności").performClick()
        composeTestRule.onNodeWithText("CO MIESIĄC").performClick()

        composeTestRule.onNodeWithTag("B").performClick()
        composeTestRule.onNodeWithText(name).assertExists()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun testDeleteExpensePlan(): Unit {
        zaloguj()
        val name: String = "GUI TEST"
        composeTestRule.onNodeWithText(appContext.getString(R.string.button2Text)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wydatki)).performClick()

        composeTestRule.onNodeWithText(appContext.getString(R.string.dodaj)).performClick()
        composeTestRule.onNodeWithText("Tytuł").performClick().performTextInput(name)

        composeTestRule.onNodeWithText("Kwota").performClick().performTextInput("19.99")

        composeTestRule.onNodeWithText("Kategoria").performClick()
        composeTestRule.onNodeWithText("TV").performClick()

        composeTestRule.onNodeWithText("Częstość płatności").performClick()
        composeTestRule.onNodeWithText("CO MIESIĄC").performClick()

        composeTestRule.onNodeWithTag("B").performClick()

        composeTestRule.onNodeWithText(name).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.usun)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.przychody)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.wydatki)).performClick()
        composeTestRule.onNodeWithText(name).assertDoesNotExist()
    }

    @Test
    fun testAddFail() {
        zaloguj()
        composeTestRule.onNodeWithText(appContext.getString(R.string.button1Text)).performClick()
        composeTestRule.onNodeWithText(appContext.getString(R.string.dodaj)).performClick()
        composeTestRule.onNodeWithTag("B").performClick()
        composeTestRule.onNodeWithTag("addLabel").assertExists()
        composeTestRule.onNodeWithText("PRZEGLĄD KONTA").assertDoesNotExist()
    }
}