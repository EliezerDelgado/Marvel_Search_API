package com.eliezer.marvel_search_api.data.firebase.configuration


import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import com.google.cloud.datastore.KeyFactory


object GoogleDataStoreConfiguration{
    //https://console.cloud.google.com/datastore/databases/-default-/entities;kind=Character;ns=FavoriteDB/query/kind?authuser=1&hl=es&project=marvel-api-favorite
    //https://cloud.google.com/datastore/docs/reference/libraries
    //https://cloud.google.com/storage/docs/reference/libraries#client-libraries-install-java
    //https://cloud.google.com/datastore/docs/concepts/entities?hl=es-419#datastore-datastore-basic-entity-java


    var datastore: Datastore? = null
        private set

    var keyFactory : KeyFactory? = null
        private set

    fun setKeyFactory(projectId: String,nameSpace : String)
    {
        keyFactory = KeyFactory(projectId)
        keyFactory?.setNamespace(nameSpace)
    }

     fun setDatastore(projectId: String) {
       datastore  = DatastoreOptions.newBuilder().setProjectId(projectId).build().getService()
     }
}