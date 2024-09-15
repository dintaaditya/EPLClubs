package com.daftech.eplclubs.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daftech.eplclubs.data.ClubRepository
import com.daftech.eplclubs.model.Club
import com.daftech.eplclubs.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ClubRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Club>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Club>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getClub(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.getClub(query)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }

    fun getFavoriteClubs() = viewModelScope.launch {
        repository.getFavoriteClub()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }
}