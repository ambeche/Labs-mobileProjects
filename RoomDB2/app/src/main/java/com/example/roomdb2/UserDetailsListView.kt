package com.example.roomdb2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.car_item.view.*

class UserDetailsListView(context: Context) : BaseAdapter() {
    var carList = emptyList<String>()
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return carList.size
    }

    override fun getItem(pos: Int): Any {
        return carList[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        val rowView = if (view === null)
            inflater.inflate(R.layout.car_item, parent,false)
        else view

        rowView.tvCarItem.text = carList[pos]

        return rowView
    }

}