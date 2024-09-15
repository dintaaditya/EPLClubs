package com.daftech.eplclubs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daftech.eplclubs.data.ClubRepository
import com.daftech.eplclubs.ui.screen.detail.DetailClubViewModel
import com.daftech.eplclubs.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: ClubRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailClubViewModel::class.java)) {
            return DetailClubViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}