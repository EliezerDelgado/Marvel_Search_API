package com.eliezer.marvel_search_api.data.expand

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities


val Context.connectivityManager: ConnectivityManager
    get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
val Context.isInternetConnected: Boolean get() =  isInternetAvailable(this)
fun Context.registerNetworkCallback(networkCallback: NetworkCallback) {
    connectivityManager.registerDefaultNetworkCallback(networkCallback)
}
fun Context.unregisterNetworkCallback(networkCallback: NetworkCallback) {
    connectivityManager.unregisterNetworkCallback(networkCallback)
}
private fun isInternetAvailable(context: Context): Boolean {
    var result = false
    context.connectivityManager.also {   cm->
        val network: Network? = cm.activeNetwork
        network?.also { net ->
            val networkCapabilities = cm.getNetworkCapabilities(net)
            networkCapabilities?.also { nc ->
                result =  (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ))
            }
        }
    }
    return result
}