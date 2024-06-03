package com.example.ucsocial

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vp_alpapp.R
import com.example.vp_alpapp.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUIView(
    loginViewModel: LoginViewModel,
    dataStore: DataStore,
    context: Context,
    navController: NavController,
    firstvalue: String
) {
    // Variabel email dan password yang bisa dirubah menggunakan TextField
    var email by rememberSaveable { mutableStateOf(firstvalue) }
    var password by rememberSaveable { mutableStateOf("") }

    var filled1 by remember { mutableStateOf(false) }
    var filled2 by remember { mutableStateOf(false) }
    // State untuk menyimpan status validasi email
    var isEmailValid by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
            .padding(30.dp)
    ) {
        Column {
            Text(
                text = "Hello.\nWelcome Back!",
                style = TextStyle(
                    fontSize = 36.sp,
//                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(800),
                    color = Color(0xFF000000),

                    )
            )
            Spacer(modifier = Modifier.height(100.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mail),
                    contentDescription = "Email Picture",
                    modifier = Modifier
                        .size(30.dp)
                )
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        // Validasi format email
                        isEmailValid = isValidEmail(email)
                        filled1 = true
                    },
                    placeholder = { Text("Email Address") },
                    isError = !isEmailValid,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color(0xFF8F8F8F),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.key),
                    contentDescription = "key Picture",
                    modifier = Modifier
                        .size(30.dp)
                )
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        filled2 = true
                    },
                    placeholder = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        cursorColor = Color(0xFF8F8F8F),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp)
                            .background(Color(0xFFF89715), RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFF89715), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                if (filled1 && filled2) {

                                    if (isEmailValid) {
                                        loginViewModel.login(
                                            dataStore = dataStore,
                                            context,
                                            email,
                                            password,
                                            navController
                                        )
                                    } else {
                                        // Tampilkan pesan kesalahan jika email tidak valid
                                        // (Anda bisa menambahkan log atau menampilkan pesan kesalahan ke pengguna)
                                        Log.d("LoginView", "Invalid email format")
                                    }
                                } else {
                                    if (!filled1 || !filled2) {
                                        // Show toast message if email or password is not filled
                                        Toast
                                            .makeText(
                                                context,
                                                "Please Enter Your Email/Password!",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                }
                            } // Added to center content vertically
                    ) {
                        Text(
                            text = "Login",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Need An Account? Click Here!",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF8C8C8C),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.clickable {
                            navController.navigate(ListScreen.Register.name)
                        }
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun loginViewUI() {
//    LoginUIView()
}