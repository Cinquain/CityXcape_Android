package com.cityxcape.cityxcape.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage


object ImageManager {

    private val storage = FirebaseStorage.getInstance().reference

    fun UploadProfileImage(context: Context, imageUri: Uri, onResult: (Result<String>) -> Unit) {

        val inputStream = context.contentResolver.openInputStream(imageUri) ?: return
        val uid: String = AuthService?.uid ?: return
        val reference = storage.child("Users/${uid}/profileImage")

        reference.putStream(inputStream)
            .addOnSuccessListener {
                reference.downloadUrl
                    .addOnSuccessListener { downloadUri ->
                         val imageUrl = downloadUri.toString()
                         DataService.saveProfileImage(imageUrl)
                         onResult(Result.success(imageUrl))
                }.addOnFailureListener { e ->
                        Log.e("Firebase", "Error saving profile image")
                        onResult(Result.failure(e))
                    }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error saving profile image to firebase", e)
                onResult(Result.failure(e))
            }

    }
}