package com.example.prvanogometnaliga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.prvanogometnaliga.viewmodel.AuthViewModel
import com.example.prvanogometnaliga.R
import com.example.prvanogometnaliga.viewmodel.FirestoreViewModel
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel


@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    footballLeagueViewModel: FootballLeagueViewModel,
    firestoreViewModel: FirestoreViewModel,
    navController: NavController
) {
    val user by authViewModel.user.collectAsState(initial = null)

    if (user != null) {
        LaunchedEffect(Unit) {
            navController.navigate(route = "home") {
                popUpTo(route = "auth") { inclusive = true }
            }
        }
    } else {
        SignInSignUpScreen(authViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInSignUpScreen(authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .padding(bottom = 32.dp) // Razmak između slike i polja za unos
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Razmak između polja za unos
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        keyboardController?.hide()
                        // Focus on password field
                    }
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Razmak između polja za unos i gumba
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        // Perform sign in/sign up
                    }
                )
            )
            Button(
                onClick = {
                    if (isSignUp) {
                        authViewModel.signUp(email, password)
                    } else {
                        authViewModel.signIn(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSignUp) "Sign Up" else "Sign In")
            }
            TextButton(onClick = { isSignUp = !isSignUp }) {
                Text(if (isSignUp) "Already have an account? Sign In" else "Don't have an account? Sign Up")
            }
        }
    }
}
