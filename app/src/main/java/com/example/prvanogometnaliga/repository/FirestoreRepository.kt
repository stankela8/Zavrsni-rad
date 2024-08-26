package com.example.prvanogometnaliga.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prvanogometnaliga.model.Comment
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getComments(matchId: String): LiveData<List<Comment>> {
        val commentsLiveData = MutableLiveData<List<Comment>>()
        firestore.collection("matches").document(matchId).collection("comments")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("FirestoreRepository", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val comments = snapshot.toObjects(Comment::class.java)
                    commentsLiveData.postValue(comments)
                } else {
                    commentsLiveData.postValue(emptyList())
                }
            }
        return commentsLiveData
    }


    fun addComment(matchId: String, comment: Comment) {
        firestore.collection("matches").document(matchId)
            .collection("comments")
            .add(comment)
            .addOnSuccessListener { Log.d("FootballRepository", "Comment added successfully!") }
            .addOnFailureListener { e -> Log.w("FootballRepository", "Error adding comment", e) }
    }
}
