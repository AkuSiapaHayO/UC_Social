package com.example.ucsocial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ucsocial.service.MyContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VPALPAPPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Routes()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    LaunchedEffect(key1 = true) {


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    VPALPAPPTheme {
//

        Routes()

//        BottomNavigationBar()
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF3F3F3)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        IconButton(onClick = {

            if (MyContainer.ACCESS_TOKEN.isEmpty()) {
                navController.navigate(ListScreen.Login.name)
            }
            else {
                navController.navigate(ListScreen.Home.name)

            }


        }) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )

        }

        IconButton(onClick = {

            if (MyContainer.ACCESS_TOKEN.isEmpty()) {
                navController.navigate(ListScreen.Login.name)
            }
            else {
                navController.navigate(ListScreen.Explore.name)

            }
        }) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        IconButton(onClick = {
            if (MyContainer.ACCESS_TOKEN.isEmpty()) {
                navController.navigate(ListScreen.Login.name)
            }
            else {
                navController.navigate(ListScreen.CreatePost.name)

            }
        }) {
            Image(
                painter = painterResource(id = R.drawable.post),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        IconButton(onClick = {


            if (MyContainer.ACCESS_TOKEN.isEmpty()) {
                navController.navigate(ListScreen.Login.name)
            }
            else {
                navController.navigate(ListScreen.Profile.name)

            }

        }) {
            Image(
                painter = painterResource(id = R.drawable.profilepic),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }

    }
}