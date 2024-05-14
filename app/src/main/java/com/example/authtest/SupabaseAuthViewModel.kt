package com.example.authtest

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authtest.data.model.UserState
import com.example.authtest.data.network.SupabaseClient.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import java.lang.Exception

class SupabaseAuthViewModel: ViewModel(){
    var userState = mutableStateOf<UserState>(UserState.Loading)
        private set

    var userEmail by mutableStateOf("")
        private set

    var userPassword by mutableStateOf("")
        private set

    fun setUpEmailPassword(user: String, pwd: String){
        userEmail = user
        userPassword = pwd
    }

    fun signUp(context: Context){
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email){
                    email = userEmail
                    password = userPassword
                }
            }catch (e: Exception){
                Log.e("exception in signup", "${e.message}")
            }
        }
    }
}