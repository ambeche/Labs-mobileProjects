package com.example.internalstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnLoadFragment {
    private val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            title = getString(R.string.title_write)
            fm.beginTransaction()
                .add(R.id.rootLayout, WriteFileFragment.newInstance(), "write")
                .commit()
        }
    }

    override fun onLoadFragment(tag: String) {
        val fragment = if (tag == getString(R.string.title_read))
            ReadFileFragment() else WriteFileFragment.newInstance()

        fm.beginTransaction()
            .replace(R.id.rootLayout, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}