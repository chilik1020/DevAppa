package com.chilik1020.devappa.ui.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chilik1020.devappa.ProxyViewModel
import org.koin.androidx.compose.getViewModel
import java.lang.NumberFormatException

@Composable
fun AddProxyPoint(modifier: Modifier = Modifier) {

    val addDialogState = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 16.dp),
            onClick = {
                addDialogState.value = true
            }
        ) {
            Text(text = "Добавить прокси")
        }
    }

    if (addDialogState.value) {
        AddProxyDialog(state = addDialogState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddProxyDialog(state: MutableState<Boolean>) {

    val proxyViewModel = getViewModel<ProxyViewModel>()

    val nameField = remember { mutableStateOf(String()) }
    val ipField = remember { mutableStateOf(String()) }
    val portField = remember { mutableStateOf(String()) }

    val errorMessage = remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = {
                errorMessage.value = null
                validateFields(nameField.value, ipField.value, portField.value)?.let {
                    Log.d("___)", "Validate $it")
                    errorMessage.value = it
                } ?: run {
                    proxyViewModel.addPoint(nameField.value, ipField.value, portField.value.toInt())
                    state.value = false
                }
            }) {
                Text(text = "Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = { state.value = false }) {
                Text(text = "Закрыть")
            }
        },
        title = { Text(text = "Добавить прокси") },
        text = {
            Column {
                OutlinedTextField(
                    modifier = Modifier.padding(top = 8.dp),
                    value = nameField.value,
                    label = { Text(text = "Имя") },

                    onValueChange = {
                        errorMessage.value = null
                        nameField.value = it
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.padding(top = 8.dp),
                    value = ipField.value,
                    label = { Text(text = "Ip") },
                    onValueChange = {
                        errorMessage.value = null
                        ipField.value = it
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.padding(top = 8.dp),
                    value = portField.value,
                    label = { Text(text = "Port") },
                    onValueChange = {
                        errorMessage.value = null
                        portField.value = it
                    }
                )

                Text(
                    text = errorMessage.value ?: String(),
                    color = Color.Red.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

        },

        )
}

private fun validateFields(name: String, ip: String, port: String): String? {

    if (name.isEmpty()) return ValidateProxyError.NAME_IS_EMPTY.message

    val ipPattern = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\$"

    if (!Regex(ipPattern).matches(ip)) return ValidateProxyError.WRONG_IP_FORMAT.message

    try {
        val portInt = port.toInt()
        if (portInt !in 0..65536) return ValidateProxyError.WRONG_PORT_NUMBER.message
    } catch (ex: NumberFormatException) {
        return ValidateProxyError.WRONG_PORT_NUMBER.message
    }

    return null
}

enum class ValidateProxyError(val message: String) {
    NAME_IS_EMPTY("Имя не может быть пустым"),
    WRONG_IP_FORMAT("Неверный формат ip адреса"),
    WRONG_PORT_NUMBER("Недопустимое значение порта")
}