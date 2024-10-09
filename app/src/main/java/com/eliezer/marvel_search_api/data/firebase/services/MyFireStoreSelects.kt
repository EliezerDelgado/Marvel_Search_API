package com.eliezer.marvel_search_api.data.firebase.services

import android.content.ContentValues.TAG
import android.util.Log
import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration.usersCollection
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyFireStoreSelects @Inject constructor() {
    fun getCharactersId(idUser : String) : Flow<Result<ArrayList<Int>>> = callbackFlow {
        usersCollection!!.also {
            val docRef = it.document(idUser).collection("characters")
            docRef.get()
                .addOnSuccessListener { result ->
                    trySend(
                        Result.success(
                            ArrayList(
                                result.map { res ->
                                    res.id.toInt()
                                }
                            )
                        )
                    )
                }
                .addOnFailureListener {ex->
                    trySend(Result.failure(ex))
                }
        }
        awaitClose {  }
    }

    fun getComicsId(idUser : String) : Flow<Result<ArrayList<Int>>> =    callbackFlow {
            usersCollection!!.also {
                val docRef = it.document(idUser).collection("comics")
                docRef.get()
                    .addOnSuccessListener { result ->
                        trySend(
                            Result.success(
                                ArrayList(
                                    result.map { res ->
                                        res.id.toInt()
                                    }
                                )
                            )
                        )
                    }
                    .addOnFailureListener { ex->
                        trySend(Result.failure(ex))
                    }
            }
            awaitClose {  }
        }
}