package com.example.vp_alpapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ucsocial.ListScreen
import com.example.ucsocial.service.MyContainer
import kotlinx.coroutines.launch

import java.util.UUID


class CreateContentViewModel: ViewModel() {

    fun createContent(
        headline: String,
        content_text: String,
        category_id: String,
        navController: NavController
    ) {


        viewModelScope.launch {

            MyContainer().myRepos.createContentWithoutPhoto(MyContainer.ACCESS_TOKEN, headline, content_text, category_id)
            navController.navigate(ListScreen.Profile.name)

        }

    }

    @SuppressLint("Recycle")
    fun uploadAndCreateContent(
        headline: String,
        image: Uri,
        content_text: String,
        category_id: Int,
        context: Context,
        navController: NavController
    ) {

        viewModelScope.launch {


            MyContainer().myRepos.createContent(MyContainer.ACCESS_TOKEN,headline,content_text,category_id,image,context)
            navController.navigate(ListScreen.Profile.name)


        }

    }



}