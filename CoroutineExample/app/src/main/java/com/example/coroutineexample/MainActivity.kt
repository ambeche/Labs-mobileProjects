package com.example.coroutineexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnToggle.setOnClickListener(ClickListener())

        val imgUrl = URL(getString(R.string.url))
        lifecycleScope.launch(Dispatchers.Main) {
           try {
               val bm = getImage(imgUrl)
               displayImage(bm)
           } catch (e: Exception) {
               e.message?.let { Log.e("download err", it) }
           }
        }
    }

    private suspend fun getImage(url: URL) : Bitmap {
        val img: Bitmap
        withContext(Dispatchers.IO) {
            val inStream = url.openStream()
            img = BitmapFactory.decodeStream(inStream)
        }
        return img
    }

    private fun displayImage(img: Bitmap) {
        imgView.setImageBitmap(img)
    }

    private inner class ClickListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            tvGreetings.text = if (tvGreetings.text === getText(R.string.short_description))
                getText(R.string.bon) else getText(R.string.short_description)
        }
    }
}