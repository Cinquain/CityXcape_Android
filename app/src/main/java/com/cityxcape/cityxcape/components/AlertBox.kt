package com.cityxcape.cityxcape.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


@Composable
fun AlertBox(
    title: String,
    message: String,
    confirmText: String = "Yes",
    dismissText: String = "No",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onCloseRequest: () -> Unit,
    showDialog: Boolean
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onCloseRequest,
            title = { Text(text = title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            text = { Text(text = message, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onCloseRequest()
                }) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    onCloseRequest()
                }) {
                    Text(dismissText)
                }
            }
        )
    }
}
