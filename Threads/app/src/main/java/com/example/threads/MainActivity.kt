package com.example.threads

import android.content.Context
import android.net.ConnectivityManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val msgHandler: Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
               txtDownloaded.text = msg.obj.toString()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isNetworkAvailable()) {
            Thread(FileDownloader(msgHandler, this)).start()
        }
    }

    private fun isNetworkAvailable() : Boolean {
       val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connManager.isDefaultNetworkActive
    }
}