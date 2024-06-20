package com.example.prvanogometnaliga.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun addComment(matchId: String, userId: String, comment: String) {
        val commentsCollection = firestore.collection("comments")
        val newComment = hashMapOf(
            "matchId" to matchId,
            "userId" to userId,
            "comment" to comment,
            "timestamp" to System.currentTimeMillis()
        )
        commentsCollection.add(newComment).await()
    }

    suspend fun getComments(matchId: String): List<String> {
        val commentsCollection = firestore.collection("comments")
        val querySnapshot = commentsCollection.whereEqualTo("matchId", matchId).get().await()
        return querySnapshot.documents.map { it.getString("comment") ?: "" }
    }
}
