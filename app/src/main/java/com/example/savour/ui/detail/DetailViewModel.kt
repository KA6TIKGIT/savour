package com.example.savour.ui.detail

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
import retrofit2.HttpException
import java.io.IOException

data class DetailUiState(
    // from the network
    val isLoading: Boolean = true,
    val food: Food? = null,
    val error: String? = null,
    // from Room (what is actually saved)
    val isFavorite: Boolean = false,
    val savedRating: Int = 0,
    val savedReview: String = "",
    // the review form while the user is editing it
    val rating: Int = 0,
    val review: String = ""
) {
    val hasUnsavedChanges: Boolean
        get() = rating != savedRating || review.trim() != savedReview

    val hasSavedReview: Boolean
        get() = savedRating > 0 || savedReview.isNotBlank()
}

class DetailViewModel(
    private val repo: FoodRepository,
    private val code: String
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    // Fill the form from the database only once, so later emissions don't wipe what the user is typing.
    private var formFilled = false

    init {
        load()
        viewModelScope.launch {
            repo.observeSaved(code).collect { saved ->
                _state.update { s ->
                    val updated = s.copy(
                        isFavorite = saved?.isFavorite == true,
                        savedRating = saved?.rating ?: 0,
                        savedReview = saved?.review.orEmpty()
                    )
                    if (formFilled) updated
                    else updated.copy(rating = updated.savedRating, review = updated.savedReview)
                }
                formFilled = true
            }
        }
    }

    fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val food = repo.getFood(code)
                _state.update {
                    it.copy(
                        isLoading = false,
                        food = food,
                        error = if (food == null) "Product not found." else null
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _state.update { it.copy(isLoading = false, error = "Couldn't reach Open Food Facts. Check your connection.") }
            } catch (e: HttpException) {
                val msg = if (e.code() == 404) "Product not found." else "Something went wrong. Try again."
                _state.update { it.copy(isLoading = false, error = msg) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Something went wrong. Try again.") }
            }
        }
    }

    fun toggleFavorite() {
        val food = _state.value.food ?: return
        viewModelScope.launch { repo.toggleFavorite(food) }
    }

    fun onRatingChange(rating: Int) {
        _state.update { it.copy(rating = rating.coerceIn(0, 5)) }
    }

    fun onReviewChange(text: String) {
        _state.update { it.copy(review = text.take(MAX_REVIEW_LENGTH)) }
    }

    fun saveReview() {
        val s = _state.value
        val food = s.food ?: return
        viewModelScope.launch { repo.saveReview(food, s.rating, s.review.trim()) }
    }

    companion object {
        const val MAX_REVIEW_LENGTH = 500

        fun factory(code: String) = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as SavourApp
                DetailViewModel(app.repository, code)
            }
        }
    }
}
