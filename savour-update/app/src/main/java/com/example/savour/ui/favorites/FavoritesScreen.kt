package com.example.savour.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.savour.data.local.SavedFood
import com.example.savour.ui.components.NutriScoreBadge
import com.example.savour.ui.components.RatingBar

@Composable
fun FavoritesScreen(
    onFoodClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)
) {
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Favorites", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        val list = favorites
        when {
            list == null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
            list.isEmpty() -> Text("No favorites yet. Tap the heart on a product to save it here.")
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(list, key = { it.code }) { food ->
                    FavoriteRow(food, onClick = { onFoodClick(food.code) })
                }
            }
        }
    }
}

@Composable
private fun FavoriteRow(food: SavedFood, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = food.imageUrl,
                contentDescription = food.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    food.name, style = MaterialTheme.typography.titleMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis
                )
                if (food.brand.isNotBlank()) {
                    Text(food.brand, style = MaterialTheme.typography.bodyMedium)
                }
                if (food.rating > 0) {
                    RatingBar(rating = food.rating, starSize = 16.dp)
                }
            }
            NutriScoreBadge(food.nutriscoreGrade)
        }
    }
}
