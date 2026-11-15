package com.example.savour.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NutriScoreBadge(grade: String?, modifier: Modifier = Modifier) {
    if (grade == null) return
    val color = when (grade) {
        "A" -> Color(0xFF038141)
        "B" -> Color(0xFF85BB2F)
        "C" -> Color(0xFFFECB02)
        "D" -> Color(0xFFEE8100)
        else -> Color(0xFFE63E11)
    }
    Box(
        modifier = modifier.size(28.dp).background(color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(grade, color = Color.White, fontWeight = FontWeight.Bold)
    }
}