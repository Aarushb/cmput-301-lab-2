package com.example.listycity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

/**
 Top-level screen for the app.
 cities: List<String> is the list of city names that this screen receives from MainActivity (state flows down).
onAddCity / onDeleteCity: (String) -> Unit are callbacks that send a city name back up to the CityRepository (events flow up). Neither callback mutates cities directly - CityListScreen never touches the repository itself, only reports what the user did.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onDeleteCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Whether the "add a city" text field is currently shown.
    var showAddCityField by remember { mutableStateOf(false) }
    // The text currently typed into that field.
    var newCityName by remember { mutableStateOf("") }
    // The city currently selected for deletion, or null if none is selected.
    var selectedCity by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("ListyCity") },
            actions = {
                TextButton(
                    onClick = { showAddCityField = !showAddCityField }
                ) {
                    Text("ADD CITY")
                }
                TextButton(
                    onClick = {
                        // Deleting is only possible once a city is selected;
                        // the button below is disabled otherwise, so this
                        // null check is mostly precautionary.
                        selectedCity?.let { city ->
                            onDeleteCity(city)
                            selectedCity = null
                        }
                    },
                    enabled = selectedCity != null
                ) {
                    Text("DELETE CITY")
                }
            }
        )

        // LazyColumn is the Compose replacement for a basic scrolling ListView.
        // selectableGroup() tells accessibility services that the rows inside
        // behave like a single-choice group, so screen readers such as
        // TalkBack announce each row's selected/not-selected state correctly.
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .selectableGroup()
        ) {
            items(cities) { city ->
                CityRow(
                    city = city,
                    selected = city == selectedCity,
                    onClick = {
                        // Tapping the selected city again deselects it.
                        selectedCity = if (selectedCity == city) null else city
                    }
                )
            }
        }

        if (showAddCityField) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City name") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            onAddCity(newCityName)
                            newCityName = ""
                            showAddCityField = false
                        }
                    }
                ) {
                    Text("CONFIRM")
                }
            }
        }
    }
}

/**
 Displays a single city name.
 Uses Modifier.selectable(not just a background colour) so that TalkBack and other accessibility services announce whether the row is selected, in addition to the visual highlight.
 */
@Composable
fun CityRow(
    city: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val textColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = city,
        fontSize = 28.sp,
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onClick)
            .semantics { contentDescription = "$city, ${if (selected) "selected" else "not selected"}" }
            .background(backgroundColor)
            .padding(horizontal = 18.dp, vertical = 14.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCityTheme {
        CityListScreen(
            cities = listOf("Edmonton", "Vancouver", "Montréal"),
            onAddCity = {},
            onDeleteCity = {}
        )
    }
}
