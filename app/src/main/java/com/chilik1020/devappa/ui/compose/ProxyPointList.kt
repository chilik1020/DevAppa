package com.chilik1020.devappa.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.chilik1020.devappa.ProxyPoint
import com.chilik1020.devappa.ProxyViewModel
import com.chilik1020.devappa.model.proxyAddress
import com.chilik1020.devappa.model.setProxy
import org.koin.androidx.compose.getViewModel


@Composable
fun ProxyPoints(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val proxyViewModel = getViewModel<ProxyViewModel>()
    val proxies = proxyViewModel.proxiesState

    OutlinedCard(shape = MaterialTheme.shapes.small) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            proxies.value.pointsList.forEach { point ->
                ProxyPoint(
                    point = point,
                    onClick = {
                        try {
                            it.setProxy(context)
                            proxyViewModel.setCurrent(it)
                        } catch (ex: SecurityException) {

                        }
                    },
                    onRemove = {
                        proxyViewModel.remove(it)
                    },
                    last = proxies.value.pointsList.last() == point
                )
            }
        }
    }
}

@Composable
private fun ProxyPoint(
    point: ProxyPoint,
    onClick: (ProxyPoint) -> Unit,
    onRemove: (ProxyPoint) -> Unit,
    last: Boolean
) {

    val moreDialogState = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke(point) }
            .padding(12.dp)) {
            Column {
                Text(
                    text = point.name,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontSize = 12.sp
                )
                Text(text = point.proxyAddress(), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                moreDialogState.value = true
            }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Удалить")
            }
        }
        if (!last) {
            Divider(modifier = Modifier.padding(horizontal = 8.dp))
        }


    }

    if (moreDialogState.value) {
        MoreDialog(moreDialogState, point, onRemove)
    }
}

@Composable
private fun MoreDialog(state: MutableState<Boolean>, point: ProxyPoint, onRemove: (ProxyPoint) -> Unit,) {
    Dialog(onDismissRequest = { state.value = false },
        content = {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(16.dp),) {
                    Text(
                        text = point.name,
                        modifier = Modifier.padding(bottom = 4.dp),
                        fontSize = 12.sp
                    )
                    Text(text = point.proxyAddress(), fontSize = 16.sp)
                    Divider(modifier = Modifier.padding(top = 8.dp))
                    Row {
                        TextButton(onClick = { state.value = false }) {
                            Text("Закрыть")
                        }

                        TextButton(onClick = { onRemove.invoke(point) }) {
                            Text("Удалить")
                        }
                    }
                }
            }
        }
    )
}