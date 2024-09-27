package com.eliezer.marvel_search_api.data.firebase.services

import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.datastore
import com.eliezer.marvel_search_api.data.firebase.configuration.GoogleDataStoreConfiguration.keyFactory
import com.google.cloud.datastore.Entity

class MyGoogleDataStoreInserts {

    fun insertCharacter(idUser : Int,idCharacter : Int)
    {
        keyFactory?.setKind("Character")

        keyFactory?.also {
            val taskKey  =  datastore!!.add(
                Entity.newBuilder(keyFactory!!.newKey())
                    .set("category", "Personal")
                    .set("done", false)
                    .set("priority", 4)
                    .set("description", "Learn Cloud Datastore")
                    .set("userId",idUser.toLong())
                    .set("characterId",idCharacter.toLong())
                    .build()
            ).key
            val s = taskKey.name
            s.toShort()
        }
    }
}