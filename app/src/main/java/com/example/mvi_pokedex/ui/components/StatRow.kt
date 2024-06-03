package com.example.mvi_pokedex.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvi_pokedex.utils.Constants.MAX_STATS

@Composable
fun StatRow(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    progress: Int,
    progressColor: Color,
    progressBackgroundColor: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth(Alignment.Start),
            fontSize = 20.sp,
        )
        Text(
            text = progress.toString(),
            modifier = Modifier
                .weight(2f)
                .padding(end = 8.dp),
            fontSize = 20.sp,
            color = textColor,
            fontWeight = FontWeight.Bold

        )

        LinearProgressIndicator(
            progress = { progress.toFloat() / MAX_STATS },
            modifier = Modifier
                .weight(6f)
                .fillMaxSize()
                .clip(RoundedCornerShape(4.dp)),
            color = progressColor,
            trackColor = progressBackgroundColor,
        )

    }
}