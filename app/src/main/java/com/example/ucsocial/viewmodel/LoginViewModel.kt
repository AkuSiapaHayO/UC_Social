package com.example.vp_alpapp.viewmodel

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vp_alpapp.DataStore
import com.example.vp_alpapp.ListScreen
import com.example.vp_alpapp.service.MyContainer
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    fun login(
        dataStore: DataStore,
        context: Context,
        email: String,
        password: String,
        navController: NavController
    ) {



        viewModelScope.launch {
            val token = MyContainer().myRepos.login(email, password)
            if(token.equals("Incorrect Password", true)){
                Toast.makeText(context, token, Toast.LENGTH_LONG).show()
            }else if(token.equals("User not found", true)){
                Toast.makeText(context, token, Toast.LENGTH_LONG).show()
            }else{



                dataStore.saveToken(token)

                dataStore.getToken.collect{token->
                    if(token != null){
                        MyContainer.ACCESS_TOKEN = token

                        MyContainer.user = MyContainer().myRepos.getUser(token)
                        //melihat token yang generated di log
                        Log.d("Token : ", MyContainer.ACCESS_TOKEN)

                        navController.navigate(ListScreen.Profile.name){
                            popUpTo(ListScreen.Login.name){inclusive = true}
                        }
                    }
                }


            }
        }

    }

    fun register(navController: NavController) {

        viewModelScope.launch {



        }

    }
}