package com.example.threads

import android.content.Context
import android.os.Handler
import android.util.Log
import java.lang.Exception
import java.net.URL


class FileDownloader (msgHandler: Handler, context: Context): Runnable {
    private val handler = msgHandler
    private val urlStr = context.getString(R.string.url)
    override fun run() {
        try {
            val url = URL( urlStr)
            val inStream = url.openStream()
            val data = inStream.bufferedReader().use { it.readText() }
            val text = StringBuilder().append(data).toString()

            val msg = handler.obtainMessage(0, text)
            handler.sendMessage(msg)

        }catch (e: Exception){
            e.message?.let { Log.e("download error", it) }
        }
    }
}