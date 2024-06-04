package com.example.mvi_pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvi_pokedex.utils.Utils.getColorByName

@Composable
fun TypeCardItem(type: String, widthType: Dp = Dp.Unspecified) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(widthType),
        colors = CardDefaults.cardColors(containerColor = getColorByName(type))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = type,
                Modifier.padding(8.dp),
                fontSize = 20.sp,
                fontWeight = Bold
            )
        }
    }
}