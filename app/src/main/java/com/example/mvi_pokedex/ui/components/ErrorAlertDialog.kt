package com.example.mvi_pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvi_pokedex.R

@Composable
fun ErrorAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String = stringResource(id = R.string.error),
    description: String = stringResource(id = R.string.error_description)
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.accept), fontWeight = Bold,fontSize = 20.sp)
                }
            },
            title = {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_error),
                        contentDescription = stringResource(id = R.string.error),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = title,
                        fontSize = 30.sp,
                        fontWeight = Bold
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = description,
                        fontSize = 20.sp
                    )

                }
            },
        )
    }
}