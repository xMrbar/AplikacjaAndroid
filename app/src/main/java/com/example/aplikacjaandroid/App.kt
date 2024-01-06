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
import com.example.aplikacjaandroid.ui.screens.MainMenuScreen
import com.example.aplikacjaandroid.ui.screens.RevenuesPlanScreen
import com.example.aplikacjaandroid.ui.screens.RevenuesScreen
import com.example.aplikacjaandroid.ui.screens.SignInScreen
import com.example.aplikacjaandroid.ui.screens.StatisticsScreen
import com.example.aplikacjaandroid.ui.screens.UserAccountScreen
import com.example.aplikacjaandroid.ui.screens.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth
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
                    onClickGoBack = { navigateDirectTo(navController, AppScreen.ExpensesPlan)})
            }

            composable( route = AppScreen.AddNewRevenuePlan.name){
                AddNewRevenuePlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onExpenseAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpensePlan) },
                    onClickGoBack = { navigateDirectTo(navController, AppScreen.RevenuesPlan)})
            }

            composable( route = AppScreen.AddNewExpense.name){
                AddNewExpenseScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenue) },
                    onOutPlanAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewOutPlan) },
                    onClickGoBack = { navigateDirectTo(navController, AppScreen.Expenses)})
            }

            composable( route = AppScreen.AddNewRevenue.name){
                AddNewRevenueScreen(modifier = Modifier
                    .fillMaxSize(),
                    onExpensesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpense)  },
                    onOutPlanAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewOutPlan) },
                    onClickGoBack = { navigateDirectTo(navController, AppScreen.Revenues)})
            }

            composable( route = AppScreen.AddNewOutPlan.name){
                AddNewOutPlanScreen(modifier = Modifier
                    .fillMaxSize(),
                    onRevenuesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewRevenue) },
                    onExpensesAddButtonClickedHandler = { navigateDirectTo(navController, AppScreen.AddNewExpense) },
                    onClickGoBack = { navigateDirectTo(navController, AppScreen.AccountBalance)})
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

    private fun signOut(navController: NavController){
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signOut()
        navController.popBackStack(AppScreen.Welcome.name, inclusive = false)
    }

    private fun navigateDirectTo(navController: NavController, appScreen: AppScreen){
        navController.popBackStack()
        navController.navigate(appScreen.name)
    }



}


