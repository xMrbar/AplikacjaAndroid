package com.example.aplikacjaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import androidx.navigation.compose.rememberNavController
import com.example.aplikacjaandroid.ui.ChangePasswordScreen
import com.example.aplikacjaandroid.ui.CreateAccountScreen
import com.example.aplikacjaandroid.ui.MainMenuScreen
import com.example.aplikacjaandroid.ui.SignInScreen
import com.example.aplikacjaandroid.ui.StatisticsScreen
import com.example.aplikacjaandroid.ui.UserAccountScreen
import com.example.aplikacjaandroid.ui.WelcomeScreen

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
    RevenuesPlan

}




@Composable
fun App(navController: NavHostController = rememberNavController()){

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
            MainMenuScreen(modifier = Modifier.fillMaxSize(),
                onAccountBalanceButtonClickedHandler = {navController.navigate((AppScreen.AccountBalance.name))},
                onBudgetPlanButtonClickedHandler = {navController.navigate((AppScreen.RevenuesPlan.name))},
                onStatisticsButtonClickedHandler = {navController.navigate((AppScreen.Statistics.name))},
                onHistoryAnalysisButtonClickedHandler = {navController.navigate((AppScreen.HistoryAnalysis.name))},
                onUserAccountButtonClickedHandler = {navController.navigate((AppScreen.UserAccount.name))},
                onLogOutButtonClickedHandler = {navigateToWelcome(navController)}
            )
        }

        composable( route = AppScreen.Statistics.name){
            StatisticsScreen(modifier = Modifier.fillMaxSize())
        }

        composable( route = AppScreen.UserAccount.name){
            UserAccountScreen(modifier = Modifier.fillMaxSize(),
                onChangePasswordButtonClickedHandler = {navController.navigate((AppScreen.ChangePassword.name))}
                )
        }

        composable( route = AppScreen.ChangePassword.name){
            ChangePasswordScreen(modifier = Modifier.fillMaxSize())
        }

        //---------------------------------------------------------------------------
        // Not decoupled yet, still activities

        composable( route = AppScreen.HistoryAnalysis.name){
            val context = LocalContext.current
            HistoryAnalysis(modifier = Modifier.fillMaxSize(), context)
        }

        composable( route = AppScreen.HistoryAnalysis.name){
            val context = LocalContext.current
            HistoryAnalysis(modifier = Modifier.fillMaxSize(), context)
        }

        composable( route = AppScreen.AccountBalance.name){
            AccountList()
        }

        composable( route = AppScreen.RevenuesPlan.name){
            RevenuesPlanList()
        }

    }

}

private fun navigateToWelcome(navController: NavController){
    navController.popBackStack(AppScreen.Welcome.name, inclusive = false)
}

