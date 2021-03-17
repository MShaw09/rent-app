package com.android.example.housingconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class MainActivity : AppCompatActivity() {

    lateinit var housingService: HousingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        housingService = Retrofit.Builder()
            .baseUrl("https://Project-3-RentWell-Server.ryynison.repl.co/")
            .addConverterFactory(GsonConverterFactory.create()).build().create()

    }
}
