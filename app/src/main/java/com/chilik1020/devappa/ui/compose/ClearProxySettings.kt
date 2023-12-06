package com.chilik1020.devappa.ui.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chilik1020.devappa.ProxyViewModel
import com.chilik1020.devappa.model.DISABLED_PROXY
import com.chilik1020.devappa.model.setProxy
import org.koin.androidx.compose.getViewModel

@Composable
fun ClearProxySettings(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val errorInfoVisibility = remember { mutableStateOf(false) }

    val proxyViewModel = getViewModel<ProxyViewModel>()

    Column(modifier = modifier) {


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 16.dp),
            onClick = {
                try {
                    errorInfoVisibility.value = false
                    DISABLED_PROXY.setProxy(context)
                    proxyViewModel.setCurrent(DISABLED_PROXY)
                } catch (ex: SecurityException) {
                    errorInfoVisibility.value = true
                }
            }
        ) {
            Text(text = "Отключить прокси")
        }
        AnimatedVisibility(visible = errorInfoVisibility.value) {
            Text(text = "Выполните в терминале команду: \n  adb shell pm grant com.chilik1020.devappa android.permission.WRITE_SECURE_SETTINGS")
        }
    }

}