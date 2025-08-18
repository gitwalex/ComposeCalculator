package com.gerwalex.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gerwalex.calculator.arithmatic.CalculatorScreen
import com.gerwalex.calculator.arithmatic.CalculateViewModel
import com.gerwalex.demo.ui.theme.ComposeCalculatorTheme

class MainActivity : ComponentActivity() {
    val calculateViewModel by viewModels<CalculateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .border(1.dp, MaterialTheme.colorScheme.outline),
                        calculateViewModel = calculateViewModel,
                    )
                }
            }
        }
    }
}

