package com.daftech.eplclubs.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daftech.eplclubs.data.ClubRepository
import com.daftech.eplclubs.model.Club
import com.daftech.eplclubs.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailClubViewModel(private val repository: ClubRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Club>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Club>>
        get() = _uiState

    fun getClubById(clubId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getClubById(clubId))
        }
    }

    fun updatePlayer(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateFavoriteClub(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getClubById(id)
            }
    }
}