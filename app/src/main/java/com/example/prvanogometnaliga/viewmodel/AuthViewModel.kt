package com.example.prvanogometnaliga.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserProfile(val name: String = "", val gender: String = "Muško", val age: String = "")

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> get() = _user

    private val _favoriteTeams = MutableStateFlow<List<String>>(emptyList())
    val favoriteTeams: StateFlow<List<String>> get() = _favoriteTeams

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> get() = _userProfile

    init {
        _user.value = auth.currentUser
        auth.currentUser?.let { loadUserData(it.uid) }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _user.value = auth.currentUser
                        auth.currentUser?.let { loadUserData(it.uid) }
                    } else {
                        _user.value = null
                    }
                }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _user.value = auth.currentUser
                        auth.currentUser?.let { saveNewUser(it.uid) }
                    } else {
                        _user.value = null
                    }
                }
        }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
        _favoriteTeams.value = emptyList()
        _userProfile.value = null
    }

    private fun saveNewUser(uid: String) {
        val userDoc = firestore.collection("users").document(uid)
        val initialData = hashMapOf(
            "favoriteTeams" to emptyList<String>(),
            "name" to "",
            "gender" to "Muško",
            "age" to ""
        )
        userDoc.set(initialData)
    }

    private fun loadUserData(uid: String) {
        val userDoc = firestore.collection("users").document(uid)
        userDoc.get().addOnSuccessListener { document ->
            if (document.exists()) {
                _favoriteTeams.value = document.get("favoriteTeams") as List<String>? ?: emptyList()
                _userProfile.value = document.toObject(UserProfile::class.java)
            }
        }
    }

    fun updateUserProfile(name: String, gender: String, age: String) {
        val userId = user.value?.uid ?: return
        val userProfile = UserProfile(name, gender, age)

        firestore.collection("users").document(userId)
            .set(userProfile)
            .addOnSuccessListener {
                _userProfile.value = userProfile
                Log.d("AuthViewModel", "User profile updated successfully")
            }
            .addOnFailureListener { e ->
                Log.w("AuthViewModel", "Error updating user profile", e)
            }
    }
    fun getUserProfile(uid: String, onComplete: (String, String) -> Unit) {
        val userDoc = firestore.collection("users").document(uid)
        userDoc.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val name = document.getString("name") ?: "Unknown"
                val age = document.getString("age") ?: "Unknown"
                onComplete(name, age)
            } else {
                onComplete("Unknown", "Unknown")
            }
        }
    }

}
