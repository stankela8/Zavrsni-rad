package com.example.prvanogometnaliga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prvanogometnaliga.repository.FootballRepository
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel



class FootballLeagueViewModelFactory(
    private val repository: FootballRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FootballLeagueViewModel(repository) as T
    }
}
