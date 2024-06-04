package com.example.mvi_pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvi_pokedex.ui.screens.home.SortOption

@Composable
fun HorizontalRadioGroup(selectedSortOption: (SortOption) -> Unit) {
    var selectedOption by rememberSaveable { mutableIntStateOf(0) }
    val options = SortOption.options

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEachIndexed { index,option ->
            Row(verticalAlignment = CenterVertically) {
                RadioButton(
                    selected = selectedOption == index,
                    colors = RadioButtonDefaults.colors(unselectedColor = MaterialTheme.colorScheme.onBackground),
                    onClick = {
                        selectedOption = index
                        selectedSortOption(option)
                    },
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = option.label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
