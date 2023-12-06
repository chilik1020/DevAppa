package com.chilik1020.devappa.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chilik1020.devappa.ProxyViewModel
import com.chilik1020.devappa.model.ProxyPoint
import com.chilik1020.devappa.model.setProxy
import org.koin.androidx.compose.getViewModel


@Composable
fun ProxyPoints(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val proxyViewModel = getViewModel<ProxyViewModel>()
    val proxies = proxyViewModel.proxiesState

    OutlinedCard(shape = MaterialTheme.shapes.small) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            proxies.value.forEach { point ->
                ProxyPoint(
                    point = point,
                    onClick = {

                        try {
                            it.setProxy(context)
                            proxyViewModel.setCurrent(it)
                        } catch (ex: SecurityException) {

                        }
                    },
                    last = proxies.value.last() == point
                )
            }
        }
    }
//    OutlinedButton(
//        onClick = { },
//        contentPadding = PaddingValues(0.dp),
//        shape = MaterialTheme.shapes.small
//    ) {
//        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
//            proxies.value.forEach { point ->
//                ProxyPoint(
//                    point = point,
//                    onClick = {
//
//                        try {
//                            it.setProxy(context)
//                            proxyViewModel.setCurrent(it)
//                        } catch (ex: SecurityException) {
//
//                        }
//                    },
//                    last = proxies.value.last() == point
//                )
//            }
//        }
//    }
}

@Composable
private fun ProxyPoint(point: ProxyPoint, onClick: (ProxyPoint) -> Unit, last: Boolean) {
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

        }
        if (!last) {
            Divider(modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}