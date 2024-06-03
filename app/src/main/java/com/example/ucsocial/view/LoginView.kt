package com.example.ucsocial

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vp_alpapp.DataStore
import com.example.vp_alpapp.ListScreen
import com.example.vp_alpapp.model.Login
import com.example.vp_alpapp.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    dataStore: DataStore,
    context: Context,
    navController: NavController,
    firstvalue: String
) {
    // Variabel email dan password yang bisa dirubah menggunakan TextField
    var email by rememberSaveable { mutableStateOf(firstvalue) }
    var password by rememberSaveable { mutableStateOf("") }

    // State untuk menyimpan status validasi email
    var isEmailValid by remember { mutableStateOf(true) }

    Column(
        Modifier.fillMaxWidth()
    ) {
        // Desain TextField untuk input email
        TextField(
            value = email,
            onValueChange = {
                email = it
                // Validasi format email
                isEmailValid = isValidEmail(email)
            },
            label = { Text("Email") },
            isError = !isEmailValid
        )

        // Desain TextField untuk input password
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        // Tombol submit
        Button(
            onClick = {
                // Cek apakah email valid sebelum melakukan login
                if (isEmailValid) {
                    loginViewModel.login(dataStore = dataStore, context, email, password, navController)
                } else {
                    // Tampilkan pesan kesalahan jika email tidak valid
                    // (Anda bisa menambahkan log atau menampilkan pesan kesalahan ke pengguna)
                    Log.d("LoginView", "Invalid email format")
                }
            }
        ) {
            Text(text = "Login")
        }
        
        
        Button(onClick = { navController.navigate(ListScreen.Register.name) }) {

            Text(text = "Register")
        }
    }
    

}

// Fungsi untuk validasi format email menggunakan regex
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailRegex.toRegex())
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginPreview() {

//    val loginViewModel: LoginViewModel = viewModel()
//    val context = LocalContext.current
//    val dataStore = DataStore(context)
//    LoginView(loginViewModel = loginViewModel, dataStore = dataStore, context = context )
}