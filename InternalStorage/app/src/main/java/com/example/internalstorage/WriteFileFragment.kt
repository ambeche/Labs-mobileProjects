package com.example.internalstorage

import android.content.Context
import android.content.Context.MODE_APPEND
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_write_to_file.*
import kotlinx.android.synthetic.main.fragment_write_to_file.view.*

const val  FILENAME = "internalFile.txt"
class WriteFileFragment : Fragment(), View.OnClickListener {
    private lateinit var activityListener: OnLoadFragment
    companion object{
        fun newInstance () = WriteFileFragment()
    }

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
        val fragLayout = inflater.inflate(R.layout.fragment_write_to_file, container, false)
        fragLayout.btnSave.setOnClickListener(this)
        fragLayout.toggleFrag.setOnClickListener(this)

        return fragLayout
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnSave -> {
                context?.openFileOutput(FILENAME, MODE_APPEND).use {stream ->
                    stream?.write("${edText.text}\n".toByteArray())
                }
                edText.text.clear()
                tvSuccess.text = getText(R.string.saved)
            }
            else -> activityListener.onLoadFragment(getString(R.string.title_read))
        }
    }
}