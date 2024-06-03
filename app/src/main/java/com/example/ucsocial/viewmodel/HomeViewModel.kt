package com.example.vp_alpapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vp_alpapp.DataStore
import com.example.vp_alpapp.ListScreen
import com.example.vp_alpapp.model.Content
import com.example.vp_alpapp.model.Pengguna
import com.example.vp_alpapp.service.MyContainer
import kotlinx.coroutines.launch


sealed interface HomeUIState {
    data class Success(val data: List<Content>,val user: Pengguna) : HomeUIState
    object Error : HomeUIState
    object Loading : HomeUIState

}
class HomeViewModel() : ViewModel() {

    var homeUIState: HomeUIState by mutableStateOf(HomeUIState.Loading)
        private set

     lateinit var data: List<Content>
    lateinit var user: Pengguna


    init {
        loadKonten()
    }

    private fun loadKonten() {


        viewModelScope.launch{
            try {
                data = MyContainer().myRepos.getAllContent(MyContainer.ACCESS_TOKEN)
                user = MyContainer().myRepos.getUser(MyContainer.ACCESS_TOKEN)
                homeUIState = HomeUIState.Success(data, user)
            }catch(e: Exception){
                Log.d("FAILED", e.message.toString())
                homeUIState = HomeUIState.Error
            }
        }
    }

    public fun logTest() {

        viewModelScope.launch {

            data = MyContainer().myRepos.getAllContent(MyContainer.ACCESS_TOKEN)

            for (konten in data) {
                Log.d("data", konten.id.toString())
                Log.d("dataheadline", konten.headline)
            }
        }
    }

    public fun logout(
        navController: NavController,
        dataStore: DataStore
    ) {

        viewModelScope.launch {

            MyContainer().myRepos.logout(MyContainer.ACCESS_TOKEN)

            MyContainer.ACCESS_TOKEN = ""

            dataStore.saveToken(MyContainer.ACCESS_TOKEN)

            navController.navigate(ListScreen.Login.name)
        }

    }




}