package com.example.authtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.authtest.data.network.SupabaseClient.supabase
import com.example.authtest.ui.theme.AuthTestTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable



@Serializable
data class Country(
    val id: Int,
    val name: String,
)

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthenticateScreen(vm = viewModel())
                }
            }
        }
    }
}

@Composable
fun CountriesList() {
    var countries by remember { mutableStateOf<List<Country>>(listOf()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            countries = supabase.from("countries")
                .select().decodeList<Country>()
        }
    }
    LazyColumn {
        items(
            countries,
            key = { country -> country.id },
        ) { country ->
            Text(
                country.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(Color.Cyan)
                    .border(4.dp, Color.Black),
            )
        }
    }
}