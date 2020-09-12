package com.example.internalstorage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_read_from_file.view.*

class ReadFileFragment : Fragment() {
    private lateinit var activityListener: OnLoadFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoadFragment) {
            activityListener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val readFragLayout = inflater.inflate(R.layout.fragment_read_from_file, container, false)
        readFragLayout.tvRead.text =
            context?.openFileInput(FILENAME)?.bufferedReader().use { stream ->
                stream?.readText() ?: getString(R.string.failed)
            }
        readFragLayout.btnToggle.setOnClickListener{
            activityListener.onLoadFragment(getString(R.string.tag_write))
        }
        return readFragLayout
    }
}