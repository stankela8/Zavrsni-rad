package com.example.prvanogometnaliga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.prvanogometnaliga.model.Comment
import com.example.prvanogometnaliga.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FirestoreViewModel(private val repository: FirestoreRepository) : ViewModel() {

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    fun addComment(matchId: String, userId: String, comment: String) {
        val commentObj = Comment(
            userId = userId,
            text = comment,
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.addComment(matchId, commentObj)
            fetchComments(matchId)
        }
    }

    fun fetchComments(matchId: String) {
        viewModelScope.launch {
            repository.getComments(matchId).asFlow().collect { commentsList ->
                _comments.value = commentsList ?: emptyList()
            }
        }
    }
}
