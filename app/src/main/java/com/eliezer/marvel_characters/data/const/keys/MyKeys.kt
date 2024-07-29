package com.eliezer.marvel_characters.data.const.keys

import android.util.Log
import java.io.FileInputStream
import java.util.Properties


const val PUBLIC_KEY = "a1dde56065e89eec589ce5fe2fc07643"
fun getPrivateKey() : String
{
    val properties = Properties()
    val fis = FileInputStream(".privateKey")
    properties.load(fis)
    val s = properties.getProperty("PRIVATE_KEY")
    Log.d("KEY",s)
    return s
}