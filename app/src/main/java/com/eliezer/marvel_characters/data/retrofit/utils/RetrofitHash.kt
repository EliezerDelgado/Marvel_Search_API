package com.eliezer.marvel_characters.data.retrofit.utils

import com.eliezer.marvel_characters.data.const.keys.PRIVATE_KEY
import com.eliezer.marvel_characters.data.const.keys.PUBLIC_KEY
import java.security.MessageDigest

object RetrofitHash {
    var publicKey : String = PUBLIC_KEY
    private var private_key : String = PRIVATE_KEY

    fun generateHash(ts : Long) : String
    {
        val _input = "$ts$private_key$publicKey"
        val bytes = MessageDigest.getInstance("MD5").digest(_input.toByteArray())
        return bytes.joinToString(""){"%02x".format(it)}
    }
}