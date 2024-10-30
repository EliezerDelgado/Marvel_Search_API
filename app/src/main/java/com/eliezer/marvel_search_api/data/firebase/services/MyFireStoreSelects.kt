package com.eliezer.marvel_search_api.data.firebase.services

import com.eliezer.marvel_search_api.data.firebase.configuration.FireStoreConfiguration.usersCollection
import com.eliezer.marvel_search_api.data.firebase.structure.FireStoreUsersStructure
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyFireStoreSelects @Inject constructor() {
    fun getCharactersId(idUser : String) : Flow<Result<ArrayList<Int>>> = callbackFlow {
        usersCollection!!.also {
            val docRef = it.document(idUser).collection(FireStoreUsersStructure.TABLE_CHARACTERS)
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
                val docRef = it.document(idUser).collection(FireStoreUsersStructure.TABLE_COMICS)
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