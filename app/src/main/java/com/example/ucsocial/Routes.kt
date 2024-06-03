package com.example.ucsocial

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucsocial.service.MyContainer
import com.example.ucsocial.view.Profile
import com.example.ucsocial.view.RegisterView
import com.example.vp_alpapp.viewmodel.CreateContentViewModel
import com.example.vp_alpapp.viewmodel.HomeUIState
import com.example.vp_alpapp.viewmodel.HomeViewModel
import com.example.vp_alpapp.viewmodel.LoginViewModel
import com.example.vp_alpapp.viewmodel.ProfileUiState
import com.example.vp_alpapp.viewmodel.ProfileViewModel
import com.example.vp_alpapp.viewmodel.RegisterViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


enum class ListScreen() {

    Home,
    Login,
    DetailKonten,
    CreatePost,
    Profile,
    Explore,
    Register,
    EditProfile,
    EditKonten,
    Blank,
    Profile2,
    CommentView,
    Chat

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun Routes() {

    val navController = rememberNavController()
    val dataStore = DataStore(LocalContext.current)


    val tokenState by dataStore.getToken.collectAsState(initial = null)


    GlobalScope.launch {
        dataStore.getToken.collect{
            if (it != null) {
                MyContainer.ACCESS_TOKEN = it
            }
        }
    }

    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ){ it ->

        NavHost(
            navController = navController,
            startDestination = ListScreen.Login.name,
            modifier = Modifier.padding(it)

        ) {
            composable(ListScreen.Login.name) {
                if (MyContainer.ACCESS_TOKEN.isNullOrEmpty()) {
                    val loginViewModel: LoginViewModel = viewModel()
                    LoginUIView(loginViewModel = loginViewModel, dataStore = dataStore, context = LocalContext.current, navController = navController, "" )
                }
                else {
                    navController.navigate(ListScreen.Home.name)
                }
            }
            composable(ListScreen.Profile.name) {
                val profileViewModel: ProfileViewModel = viewModel()
                val exploreViewModel: ExploreViewModel = viewModel()
                val status = profileViewModel.profileUiState
                when (status) {
                    is ProfileUiState.Loading -> {
                        Blank()
                    }
                    is ProfileUiState.Success -> {
                        Profile(navController, status.data, status.data1, exploreViewModel, profileViewModel)
                    }
                    is ProfileUiState.Error -> {

                    }
                }
            }

            composable(ListScreen.CreatePost.name) {
                val createContentViewModel: CreateContentViewModel = viewModel()
                AddPostView(createContent = createContentViewModel, context = LocalContext.current, navController)
            }

            composable(ListScreen.Register.name) {
                val registerViewModel: RegisterViewModel = viewModel()
                RegisterView(navController = navController, registerViewModel = registerViewModel)
            }
            composable(ListScreen.Home.name) {
                val homeViewModel: HomeViewModel = viewModel()

                val profileViewModel: ProfileViewModel = viewModel()

                val exploreViewModel: ExploreViewModel = viewModel()

                val status = homeViewModel.homeUIState

                when (status) {
                    is HomeUIState.Loading -> {
                        Blank()
                    }
                    is HomeUIState.Success -> {
                        Home(navController, homeViewModel = homeViewModel, user = status.user, dataStore = dataStore, exploreViewModel = exploreViewModel, listData = status.data)
                    }
                    is HomeUIState.Error -> {

                    }
                    else -> {}
                }
            }

            composable(ListScreen.Login.name+"/{email}") {
                if (MyContainer.ACCESS_TOKEN.isEmpty()) {
                    val loginViewModel: LoginViewModel = viewModel()

                    it.arguments?.let { it1 -> LoginUIView(loginViewModel = loginViewModel, dataStore = dataStore, context = LocalContext.current, navController = navController, it1.getString("email", "") ) }

                }
                else {
                    navController.navigate(ListScreen.Profile.name)

                }
            }

            composable(ListScreen.Blank.name) {
                Blank()
            }

        }
    }

}