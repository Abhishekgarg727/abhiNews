package com.abhishek.news

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDex
import com.abhishek.news.utils.LoggerUtils
import com.abhishek.news.utils.MutableLiveDataUtils
import java.lang.ref.WeakReference

/**
 * Created by Abhishek Garg on 16/05/20 - https://www.linkedin.com/in/abhishekgarg727/

 */
class MainApplication : Application() {

    companion object {
        private lateinit var context: WeakReference<Context>
        private var isConnectedToNetwork: Boolean = false

        @JvmStatic
        fun isConnectedToNetwork(): Boolean {
            return isConnectedToNetwork
        }

        @JvmStatic
        fun getAppContext(): Context? {
            return context.get()
        }
    }

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkRequest: NetworkRequest
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback


    override fun onCreate() {
        super.onCreate()
        context = WeakReference(applicationContext)
        initialiseNetworkConnectionManager()
    }


    private fun initialiseNetworkConnectionManager() {
        networkRequest = NetworkRequest.Builder().build()
        connectivityManager =
            getAppContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = getNetworkConnectionCallback()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun getNetworkConnectionCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                super.onLost(network)
                LoggerUtils.debug("network", "not unavailable")
                isConnectedToNetwork = false
            }

            override fun onUnavailable() {
                super.onUnavailable()
                LoggerUtils.debug("network", "not unavailable")
                isConnectedToNetwork = false

            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                LoggerUtils.debug("network", "available")
                isConnectedToNetwork = true
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}