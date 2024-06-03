package com.example.vp_alpapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ucsocial.model.Content
import com.example.ucsocial.model.Pengguna
import com.example.ucsocial.service.MyContainer
import kotlinx.coroutines.launch

class ExploreViewModel: ViewModel() {

    sealed interface ExploreUiState {
        data class Success(val data: List<Content>, val datauser: Pengguna) : ExploreUiState
        object Error : ExploreUiState
        object Loading : ExploreUiState

    }
    var exploreUiState:  ExploreUiState by mutableStateOf( ExploreUiState.Loading)
        private set

    lateinit var data: List<Content>
    lateinit var datauser: Pengguna

    init {
        loadKonten()
    }

    private fun loadKonten() {


        viewModelScope.launch{
            try {
                datauser = MyContainer().myRepos.getUser(MyContainer.ACCESS_TOKEN)
                data = MyContainer().myRepos.getAllContent(MyContainer.ACCESS_TOKEN)
                exploreUiState =  ExploreUiState.Success(data,datauser)
            }catch(e: Exception){
                Log.d("FAILED", e.message.toString())
                exploreUiState =  ExploreUiState.Error
            }
        }
    }

    fun delete(

        kontenId: String

    ) {


        viewModelScope.launch{
            MyContainer().myRepos.delete(MyContainer.ACCESS_TOKEN,kontenId)

        }



    }

    fun edit(
        kontenId: String,
        headline: String,
        categoryId: String,
        context_text: String,

    ) {

        viewModelScope.launch {

            MyContainer().myRepos.updateContent(MyContainer.ACCESS_TOKEN, kontenId, headline = headline, context_text , categoryId = categoryId)

        }
    }

    fun getUserById(

        navController: NavController,
        id: String

    ) {

        viewModelScope.launch {

            val user = MyContainer().myRepos.getUserbyId(MyContainer.ACCESS_TOKEN, id)

            Log.d("p", user.name)
            Log.d("p", user.id.toString())


        }

    }


}
