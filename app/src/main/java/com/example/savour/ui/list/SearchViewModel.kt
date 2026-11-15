package com.example.savour.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.savour.SavourApp
import com.example.savour.data.repository.Food
import com.example.savour.data.repository.FoodRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<Food> = emptyList(),
    val error: String? = null,
    val hasSearched: Boolean = false
)

class SearchViewModel(private val repo: FoodRepository) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    fun onQueryChange(text: String) {
        _state.update { it.copy(query = text) }
    }

    fun search() {
        val q = _state.value.query.trim()
        if (q.isEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val results = repo.search(q)
                _state.update { it.copy(isLoading = false, results = results, hasSearched = true) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _state.update { it.copy(isLoading = false, error = "Couldn't reach Open Food Facts. Check your connection.") }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Something went wrong. Try again.") }
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SavourApp
                SearchViewModel(app.repository)
            }
        }
    }
}