package com.example.imagecapture2

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 11
    private lateinit var currentImgPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imgFile: File?
        imgFile = File.createTempFile("photo", ".jpg", imgPath)

        val imgUri: Uri = FileProvider.getUriForFile(
                this, "com.example.imagecapture2.fileprovider", imgFile)

        currentImgPath = imgFile.absolutePath
        val imgIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imgIntent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        imgIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)

        if (imgIntent.resolveActivity(packageManager) !== null) {
            startActivityForResult(imgIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imgBitmap = BitmapFactory.decodeFile(currentImgPath)

        imgFromFile.setImageBitmap(imgBitmap)
        imgFromFile.rotation = 90F
    }
}