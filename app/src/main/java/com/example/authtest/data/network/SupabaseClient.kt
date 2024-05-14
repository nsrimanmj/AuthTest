package com.example.authtest.data.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://newtaomfmnsmxxjxhdtd.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5ld3Rhb21mbW5zbXh4anhoZHRkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM3NzcxMzMsImV4cCI6MjAyOTM1MzEzM30.1qEGnTQqDaMecWH2cRARv6g0LQCeUNJE2f8ZQADbyv0"
    ) {
        install(Postgrest)
        install(Auth)
    }
}