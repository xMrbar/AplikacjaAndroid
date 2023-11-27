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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aplikacjaandroid.ui.theme.AplikacjaAndroidTheme
import androidx.navigation.compose.rememberNavController
import com.example.aplikacjaandroid.ui.CreateAccountScreen
import com.example.aplikacjaandroid.ui.SignInScreen
import com.example.aplikacjaandroid.ui.WelcomeScreen

enum class AppScreen() {
    Welcome,
    SignIn,
    CreateAccount
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
            SignInScreen(modifier = Modifier.fillMaxSize())
        }

        composable( route = AppScreen.CreateAccount.name){
            CreateAccountScreen(modifier = Modifier.fillMaxSize())
        }

    }


}

