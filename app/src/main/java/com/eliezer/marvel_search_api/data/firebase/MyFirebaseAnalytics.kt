package com.eliezer.marvel_search_api.data.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class MyFirebaseAnalytics(private val firebaseAnalytics: FirebaseAnalytics) {

    fun selectItemEvent(id : String,name : String,type:String)
    {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,bundle)
    }
}