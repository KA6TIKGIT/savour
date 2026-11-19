package com.example.savour.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val StarGold = Color(0xFFFFB300)

/**
 * Five stars. Pass [onRatingChange] to make it tappable, leave it null for a read-only display.
 * Tapping the currently selected star clears the rating back to 0.
 */
@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    starSize: Dp = 32.dp,
    onRatingChange: ((Int) -> Unit)? = null
) {
    Row(modifier) {
        for (star in 1..5) {
            val tint = if (star <= rating) StarGold else MaterialTheme.colorScheme.outlineVariant
            if (onRatingChange != null) {
                IconButton(onClick = { onRatingChange(if (star == rating) 0 else star) }) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rate $star of 5",
                        tint = tint,
                        modifier = Modifier.size(starSize)
                    )
                }
            } else {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(starSize)
                )
            }
        }
    }
}
