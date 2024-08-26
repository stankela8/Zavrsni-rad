package com.example.prvanogometnaliga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prvanogometnaliga.repository.FirestoreRepository
import com.example.prvanogometnaliga.repository.FootballRepository
import com.example.prvanogometnaliga.viewmodel.AuthViewModel
import com.example.prvanogometnaliga.viewmodel.FootballLeagueViewModel


class FootballLeagueViewModelFactory(
    private val repository: FootballRepository,
    private val firestore: FirestoreRepository,
    private val authViewModel: AuthViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FootballLeagueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FootballLeagueViewModel(repository, firestore, authViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
