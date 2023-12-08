package com.chilik1020.devappa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chilik1020.devappa.ui.compose.AddProxyPoint
import com.chilik1020.devappa.ui.compose.ClearProxySettings
import com.chilik1020.devappa.ui.compose.CurrentProxySettings
import com.chilik1020.devappa.ui.compose.ProxyPoints
import com.chilik1020.devappa.ui.theme.DevAppaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevAppaTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.padding(16.dp)) {
                        CurrentProxySettings()
                        ClearProxySettings()
                        AddProxyPoint()
                        ProxyPoints()
                    }
                }
            }
        }
    }
}




