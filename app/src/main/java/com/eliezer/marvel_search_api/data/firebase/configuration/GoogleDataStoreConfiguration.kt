package com.eliezer.marvel_search_api.data.firebase.configuration
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
object GoogleDataStoreConfiguration
{
    val firestore: FirebaseFirestore? get() = getFireStore()
    private fun getFireStore() : FirebaseFirestore?=
        try {
            Firebase.firestore
        }
        catch (e : Exception)
        {
            Log.e(TAG,e.message.toString())
            null
        }
}