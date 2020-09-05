package com.example.imagecapture

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (imgIntent.resolveActivity(packageManager) !== null) {
            startActivityForResult(imgIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode,  data)
        if (requestCode === REQUEST_IMAGE_CAPTURE && resultCode === Activity.RESULT_OK) {
            val imgBitmap: Bitmap = data?.extras?.get("data") as Bitmap
            imgCaptured.setImageBitmap(imgBitmap)
        }
    }
}