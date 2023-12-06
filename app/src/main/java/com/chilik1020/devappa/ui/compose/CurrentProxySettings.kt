package com.chilik1020.devappa.ui.compose

import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chilik1020.devappa.ProxyViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentProxySettings(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val proxyViewModel = getViewModel<ProxyViewModel>()
    val currentProxy = proxyViewModel.currentProxy

    val readSettings = {
        val proxy = Settings.Global.getString(context.contentResolver, Settings.Global.HTTP_PROXY)
        Log.d("___)", "READ PROXY $proxy")
        proxyViewModel.setCurrent(proxy)
    }

    LaunchedEffect(true) {
        readSettings.invoke()
    }


    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        value = currentProxy.value,
        textStyle = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        trailingIcon = {
            IconButton(onClick = { readSettings.invoke() }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Обновить")
            }
        },
        readOnly = true,
        label = { Text(text = "Системный прокси") },
        onValueChange = { })

}