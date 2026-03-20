package com.gerwalex.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gerwalex.calculator.CalculatorDialog
import com.gerwalex.calculator.ui.theme.CalculatorAppTheme
import java.math.BigDecimal

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var openDialog by remember { mutableStateOf(false) }
            CalculatorAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold { innerPadding ->
                        Box(
                            modifier = Modifier.padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = { openDialog = true }) {
                                Text("Open Dialog")
                            }
                            if (openDialog) {
                                CalculatorDialog(
                                    initialValue = BigDecimal(123),
                                    onResult = {},
                                    onDismissRequest = { openDialog = false })
                            }
                        }
                    }
                }
            }
        }
    }
}

