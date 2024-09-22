package com.eliezer.marvel_search_api.data.firebase.utils

import java.security.MessageDigest
import java.util.UUID

object FirebaseNonce {
    val md: MessageDigest = MessageDigest.getInstance("SHA-256")
    fun generateNonce() : String
    {
        val ranNonce: String = UUID.randomUUID().toString()
        val bytes: ByteArray = ranNonce.toByteArray()
        val digest: ByteArray = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}