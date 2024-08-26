package com.example.prvanogometnaliga.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prvanogometnaliga.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val user by authViewModel.user.collectAsState()
    val favoriteTeams by authViewModel.favoriteTeams.collectAsState()
    val userProfile by authViewModel.userProfile.collectAsState()
    var showConfirmation by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showConfirmation) {
                Text(
                    text = "Data saved successfully",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            user?.let {
                Text(text = "Logged in as: ${it.email}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "User Information",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Forma za korisničke podatke
            userProfile?.let {
                UserProfileForm(
                    name = it.name,
                    gender = it.gender,
                    age = it.age,
                    onSave = { name, gender, age ->
                        authViewModel.updateUserProfile(name, gender, age)
                        showConfirmation = true
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileForm(
    name: String,
    gender: String,
    age: String,
    onSave: (String, String, String) -> Unit
) {
    var nameState by remember { mutableStateOf(TextFieldValue(name)) }
    var genderState by remember { mutableStateOf(gender) }
    var ageState by remember { mutableStateOf(TextFieldValue(age)) }

    Column {
        OutlinedTextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Gender", style = MaterialTheme.typography.bodyMedium)
        Row {
            RadioButton(
                selected = genderState == "Male",
                onClick = { genderState = "Male" }
            )
            Text(text = "Male", modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = genderState == "Female",
                onClick = { genderState = "Female" }
            )
            Text(text = "Male", modifier = Modifier.align(Alignment.CenterVertically))
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ageState,
            onValueChange = { ageState = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSave(nameState.text, genderState, ageState.text)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save")
        }
    }
}