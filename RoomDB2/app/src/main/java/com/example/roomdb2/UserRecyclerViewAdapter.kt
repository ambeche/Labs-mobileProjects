package com.example.roomdb2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.users_item.view.*

class UserRecyclerViewAdapter(private val activityListener: OnLoadFragment ) :
    RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    private var userData = emptyList<UserWithCars>()


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.users_item,
            parent, false))

    override fun getItemCount() = userData.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userData[position]
        val row = holder.itemView
        row.tvName.text = item.user.lastName
        row.tvCity.text = item.user.address.city

        if (item.cars.isNotEmpty()) {
            row.tvCar.text =  item.cars[0].make
            row.tvValue.text = item.cars[0].carValue.toString()
        }

        row.setOnClickListener {
            activityListener.onLoadFragmentWithArg(item)
        }
    }

    fun setUserData (data: List<UserWithCars>) {
        userData = data
        notifyDataSetChanged()
    }
}