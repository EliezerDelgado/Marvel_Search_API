package com.eliezer.marvel_search_api.data.firebase.configuration


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object GoogleDataStoreConfiguration{
    //https://console.cloud.google.com/datastore/databases/-default-/entities;kind=Character;ns=FavoriteDB/query/kind?authuser=1&hl=es&project=marvel-api-favorite
    //https://cloud.google.com/datastore/docs/reference/libraries
    //https://cloud.google.com/storage/docs/reference/libraries#client-libraries-install-java
    //https://cloud.google.com/datastore/docs/concepts/entities?hl=es-419#datastore-datastore-basic-entity-java
    //https://github.com/googleapis/google-cloud-java#specifying-a-project-id
    //https://cloud.google.com/datastore/docs/datastore-api-tutorial?hl=es-419#java

    var firestore: FirebaseFirestore? = null
    private set


     fun setFiresStore() {
     //    GoogleCredentials
         //    https://developers.google.com/identity/protocols/oauth2?hl=es-419
         //    https://developers.google.com/identity/protocols/oauth2/native-app?hl=es-419
         //https://github.com/googleapis/google-auth-library-java
         firestore = Firebase.firestore
     }
}