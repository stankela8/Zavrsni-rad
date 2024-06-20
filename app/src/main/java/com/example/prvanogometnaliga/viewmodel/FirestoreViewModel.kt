package com.example.prvanogometnaliga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prvanogometnaliga.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirestoreViewModel(private val repository: FirestoreRepository) : ViewModel() {

    private val _comments = MutableStateFlow<List<String>>(emptyList())
    val comments: StateFlow<List<String>> = _comments

    fun addComment(matchId: String, userId: String, comment: String) {
        viewModelScope.launch {
            repository.addComment(matchId, userId, comment)
            fetchComments(matchId)
        }
    }

    fun fetchComments(matchId: String) {
        viewModelScope.launch {
            _comments.value = repository.getComments(matchId)
        }
    }
}
