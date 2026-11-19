package com.example.savour.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.savour.SavourApp
import com.example.savour.data.local.SavedFood
import com.example.savour.data.repository.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(repo: FoodRepository) : ViewModel() {

    // null = still loading from Room, empty list = no favorites yet
    val favorites: StateFlow<List<SavedFood>?> = repo.observeFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SavourApp
                FavoritesViewModel(app.repository)
            }
        }
    }
}
