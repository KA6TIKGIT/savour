package com.example.savour.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.savour.data.repository.Food
import com.example.savour.ui.components.NutriScoreBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    code: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModel.factory(code))
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(state.food?.name ?: "Product", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (state.food != null) {
                        IconButton(onClick = viewModel::toggleFavorite) {
                            Icon(
                                imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (state.isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = if (state.isFavorite) MaterialTheme.colorScheme.error else LocalContentColor.current
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            val food = state.food
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                state.error != null -> Column(
                    Modifier.align(Alignment.Center).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.error!!, color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = viewModel::load) { Text("Retry") }
                }

                food != null -> Column(
                    Modifier
                        .fillMaxSize()
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductInfo(food)

                    HorizontalDivider()

                    ReviewForm(
                        rating = state.rating,
                        review = state.review,
                        hasUnsavedChanges = state.hasUnsavedChanges,
                        hasSavedReview = state.hasSavedReview,
                        onRatingChange = viewModel::onRatingChange,
                        onReviewChange = viewModel::onReviewChange,
                        onSave = viewModel::saveReview
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductInfo(food: Food) {
    AsyncImage(
        model = food.imageUrl ?: food.thumbUrl,
        contentDescription = food.name,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )

    Text(food.name, style = MaterialTheme.typography.headlineSmall)

    if (food.brand.isNotBlank()) {
        Text(
            food.brand,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Nutri-Score", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(8.dp))
        if (food.nutriscoreGrade != null) {
            NutriScoreBadge(food.nutriscoreGrade)
        } else {
            Text("not available", style = MaterialTheme.typography.bodyMedium)
        }
    }

    food.categories?.let { InfoSection("Categories", it) }
    InfoSection("Ingredients", food.ingredients ?: "No ingredients listed.")
}

@Composable
private fun InfoSection(title: String, body: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(body, style = MaterialTheme.typography.bodyMedium)
    }
}
