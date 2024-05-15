package com.example.authtest

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authtest.data.model.UserState
import com.example.authtest.data.network.SupabaseClient.supabase
import com.example.authtest.utils.SharedPreferencesHelper
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import kotlin.Exception

class SupabaseAuthViewModel: ViewModel(){
    private var userState = mutableStateOf<UserState>(UserState.Loading)

    val _userState: State<UserState> = userState



    fun signUp(context: Context, userEmail:String, userPassword:String){
        viewModelScope.launch {
            try {
                userState.value = UserState.Loading
                supabase.auth.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                userState.value = UserState.Success("registered in user successfully")
            }catch (e: Exception){
                Log.e("exception in signup", "${e.message}")
                userState.value = UserState.Failure("Error ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context){
        val accessToken = supabase.auth.currentAccessTokenOrNull()
        val sharedPref = SharedPreferencesHelper(context)
        sharedPref.saveStringData("accessToken", accessToken)
    }

    private fun getToken(context: Context): String?{
        val sharedPref = SharedPreferencesHelper(context)
        return sharedPref.getStringData("accessToken")
    }


    fun logout(){
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                userState.value = UserState.Success("logged out user")
            }
            catch (e: Exception){
                userState.value = UserState.Failure("Unable to logout user")
            }
        }
    }

    fun isUserLoggedIn(context: Context){
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if(token.isNullOrEmpty()){
                    userState.value = UserState.Failure("User not logged in")
                }else{
                    supabase.auth.retrieveUser(token)
                    supabase.auth.refreshCurrentSession()
                    saveToken(context)
                    userState.value = UserState.Success("User is logged in")
                }
            }catch (e:Exception){
                userState.value = UserState.Failure("Error occurred ${e.message}")
            }
        }
    }

}