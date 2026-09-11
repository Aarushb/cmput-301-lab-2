package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {

    // MainActivity inherits Android activity behaviour from ComponentActivity
    // (OOP: inheritance).
    // The repository is created once per Activity and owns the app's city data for as long as the screen is alive.
    private val cityRepository = CityRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        onDeleteCity = { cityRepository.removeCity(it) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
