package com.example.savour.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.savour.ui.components.RatingBar

@Composable
fun ReviewForm(
    rating: Int,
    review: String,
    hasUnsavedChanges: Boolean,
    hasSavedReview: Boolean,
    onRatingChange: (Int) -> Unit,
    onReviewChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Your review", style = MaterialTheme.typography.titleMedium)

        RatingBar(rating = rating, onRatingChange = onRatingChange)

        OutlinedTextField(
            value = review,
            onValueChange = onReviewChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("What did you think?") },
            supportingText = { Text("${review.length} / ${DetailViewModel.MAX_REVIEW_LENGTH}") },
            minLines = 3
        )

        Button(
            onClick = onSave,
            enabled = hasUnsavedChanges,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(if (!hasUnsavedChanges && hasSavedReview) "Saved" else "Save review")
        }
    }
}
