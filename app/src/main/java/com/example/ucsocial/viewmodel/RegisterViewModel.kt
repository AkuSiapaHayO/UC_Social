package com.example.vp_alpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ucsocial.ListScreen
import com.example.ucsocial.service.MyContainer
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {


    public fun register(

        email:String,
        pasword: String,
        nim: String,
        prodi_id: Int,
        name: String,
        navController: NavController

    ) {

            viewModelScope.launch {

                val user = MyContainer().myRepos.register(email,name, nim,pasword, prodi_id = prodi_id).email

                navController.navigate(ListScreen.Login.name+"/"+user)

            }

    }

}

