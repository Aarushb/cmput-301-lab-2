package com.example.listycity

import androidx.compose.runtime.mutableStateListOf

/**
 Stores the city names used by the app.
 This follows the same encapsulation idea as the Pet classes from Lab 1:
 the mutable list [_cities] is private, so no other class can change it directly.
 The read-only [cities] property is what the UI observes
 [addCity] / [removeCity] are the *only* way the list can be modified.
 This keeps mutation behind these two functions is what keeps the on-screen list consistent with the underlying data.
 [_cities] is a Compose SnapshotStateList (via mutableStateListOf) rather than a plain MutableList
 so any composable reading [cities] automatically recomposes when a city is added or removed.
 */
class CityRepository {

    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow",
        "Sydney", "Berlin", "Vienna",
        "Tokyo", "Beijing", "Osaka",
        "New Delhi"
    )

    // Read-only view of the list for the UI to display.
    val cities: List<String>
        get() = _cities

    /** Adds [city] to the end of the list. */
    fun addCity(city: String) {
        _cities.add(city)
    }

    /** Removes the first occurrence of [city] from the list, if present. */
    fun removeCity(city: String) {
        _cities.remove(city)
    }
}
