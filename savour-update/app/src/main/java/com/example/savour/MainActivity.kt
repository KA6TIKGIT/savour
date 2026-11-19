package com.example.savour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.savour.navigation.SavourNavGraph
import com.example.savour.ui.theme.SavourTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SavourTheme {
                SavourNavGraph()
            }
        }
    }
}
