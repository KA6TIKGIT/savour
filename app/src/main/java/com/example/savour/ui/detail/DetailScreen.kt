package com.example.savour.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(productId: String, viewModel: DetailViewModel = hiltViewModel()) {
    // TODO: Load product details using the viewModel
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Product Details") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Placeholder UI
            Text(text = "Details for product: $productId")
            Spacer(modifier = Modifier.height(8.dp))
            RatingBar(rating = 4.5f, onRatingChanged = { /* TODO */ })
            Spacer(modifier = Modifier.height(8.dp))
            ReviewForm(productId = productId)
        }
    }
}
