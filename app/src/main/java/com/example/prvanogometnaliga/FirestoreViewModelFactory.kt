package com.example.prvanogometnaliga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prvanogometnaliga.repository.FirestoreRepository
import com.example.prvanogometnaliga.viewmodel.FirestoreViewModel

class FirestoreViewModelFactory(
    private val repository: FirestoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FirestoreViewModel(repository) as T
    }
}