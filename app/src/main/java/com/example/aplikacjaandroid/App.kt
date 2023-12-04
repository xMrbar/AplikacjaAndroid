package com.example.aplikacjaandroid

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplikacjaandroid.ui.AccountScreen
import com.example.aplikacjaandroid.ui.AddNewExpensePlanScreen
import com.example.aplikacjaandroid.ui.AddNewExpenseScreen
import com.example.aplikacjaandroid.ui.AddNewOutPlanScreen
import com.example.aplikacjaandroid.ui.AddNewRevenuePlanScreen
import com.example.aplikacjaandroid.ui.AddNewRevenueScreen
import com.example.aplikacjaandroid.ui.ChangePasswordScreen
import com.example.aplikacjaandroid.ui.CreateAccountScreen
import com.example.aplikacjaandroid.ui.CreateFile
import com.example.aplikacjaandroid.ui.ExpensesPlanScreen
import com.example.aplikacjaandroid.ui.ExpensesScreen
import com.example.aplikacjaandroid.ui.MainMenuScreen
import com.example.aplikacjaandroid.ui.RevenuesPlanScreen
import com.example.aplikacjaandroid.ui.RevenuesScreen
import com.example.aplikacjaandroid.ui.SignInScreen
import com.example.aplikacjaandroid.ui.StatisticsScreen
import com.example.aplikacjaandroid.ui.UserAccountScreen
import com.example.aplikacjaandroid.ui.WelcomeScreen
import dagger.hilt.android.HiltAndroidApp

enum class AppScreen() {
    Welcome,
    SignIn,
    CreateAccount,
    MainMenu,
    Statistics,
    UserAccount,
    ChangePassword,
    HistoryAnalysis,
    AccountBalance,
    Revenues,
    Expenses,
    RevenuesPlan,
    ExpensesPlan,
    AddNewExpense,
    AddNewRevenue,
    AddNewOutPlan,
    AddNewExpensePlan,
    AddNewRevenuePlan
}

@HiltAndroidApp
class FinancialApp: Application(){



    @Composable
    fun initNav(navController: NavHostController = rememberNavController()){

        NavHost(
            navController = navController,
            startDestination = AppScreen.Welcome.name,
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
                    onLogOutButtonClickedHandler = { navigateToWelcome(navController) }
                )
            }

            composable( route = AppScreen.Statistics.name){
                StatisticsScreen(modifier = Modifier.fillMaxSize())
            }

            composable( route = AppScreen.UserAccount.name){
                UserAccountScreen(
                    modifier = Modifier.fillMaxSize(),
                    onChangePasswordButtonClickedHandler = {navController.navigate((AppScreen.ChangePassword.name))}
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
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenuePlan) })
            }

            composable( route = AppScreen.AddNewRevenuePlan.name){
                AddNewRevenuePlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onExpenseAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpensePlan) })
            }

            composable( route = AppScreen.AddNewExpense.name){
                AddNewExpenseScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenue) },
                    onOutPlanAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewOutPlan) })
            }

            composable( route = AppScreen.AddNewRevenue.name){
                AddNewRevenueScreen(modifier = Modifier
                    .fillMaxSize(),
                    onExpensesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpense)  },
                    onOutPlanAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewOutPlan) })
            }

            composable( route = AppScreen.AddNewOutPlan.name){
                AddNewOutPlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenue) },
                    onExpensesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpense) })
            }

            //---------------------------------------------------------------------------
            // Not decoupled yet, still activities

            composable( route = AppScreen.HistoryAnalysis.name){
                val context = LocalContext.current
                HistoryAnalysis(modifier = Modifier.fillMaxSize(), context)
            }
        }
    }

    private fun navigateToWelcome(navController: NavController){
        navController.popBackStack(AppScreen.Welcome.name, inclusive = false)
    }

    private fun navigateDirectTo(navController: NavController, appScreen: AppScreen){
        navController.popBackStack()
        navController.navigate(appScreen.name)
    }



}


