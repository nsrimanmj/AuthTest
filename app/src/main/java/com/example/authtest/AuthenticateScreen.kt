package com.example.authtest

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.authtest.data.model.UserState

@Composable
fun AuthenticateScreen(modifier: Modifier = Modifier){

    val vm:SupabaseAuthViewModel = SupabaseAuthViewModel()
    val ctx = LocalContext.current
    val userState by vm._userState
    LaunchedEffect(Unit) {
        vm.isUserLoggedIn(ctx)

    }
    var message by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center,

        
    ){
        val focusManager = LocalFocusManager.current
        var username by remember {
            mutableStateOf("")
        }

        var password by remember{
            mutableStateOf("")
        }


        val showPassword = remember { mutableStateOf(false) }



        Column{
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = {
                username = it
            },
                leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "username")},
                placeholder = { Text(text = "Enter username")},
                label = { Text(text = "Username")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                password = it
            },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password")},
                placeholder = { Text(text = "Enter password")},
                label = { Text(text = "Password")},
                keyboardOptions = KeyboardOptions(autoCorrect = false),
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val (icon, iconColor) = if (showPassword.value) {
                        Pair(
                            Icons.Filled.Lock,
                            Color.Black
                        )
                    } else {
                        Pair(Icons.Filled.ThumbUp, Color.Black)
                    }

                    IconButton(onClick = { showPassword.value = !showPassword.value }) {
                        Icon(
                            icon,
                            contentDescription = "Visibility",
                            tint = iconColor
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { vm.signUp(context = ctx, userEmail = username, userPassword = password)
                             }, modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))) {
                Text(text = "Submit")
            }
            Spacer(modifier = Modifier.height(20.dp))
            

            when(userState){
                is UserState.Loading ->{
                    Text(text = "Loading")
                }

                is UserState.Success -> {
                    val msg = (userState as UserState.Success).msg
                    message = msg
                }
                is UserState.Failure -> {
                    val msg = (userState as UserState.Failure).msg
                    message = msg
                }
            }
            if(message.isNotEmpty()){
                Text(text = message)
            }

        }
    }
}



@Preview(name = "Preview", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun AuthenticateScreenPreview(modifier:Modifier = Modifier){
    AuthenticateScreen()
}