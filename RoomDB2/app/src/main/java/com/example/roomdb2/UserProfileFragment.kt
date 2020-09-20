package com.example.roomdb2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_user_profile.view.*

class UserProfileFragment : Fragment() {
  private lateinit var listAdapter: UserDetailsListView

    companion object {
        fun fragInstance(user: UserWithCars) : UserProfileFragment {
            val fragment = UserProfileFragment()
            // bundle is passed to new instance of frag via its argument property
            val bundle = bundleOf(
               "name" to user.user.toString(),
                "id" to user.user.userId,
                "address" to user.user.address.toString(),
                "numCars" to "${user.cars.size}",
            )

            if (user.cars.isNotEmpty()) {
                for (i in user.cars.indices) {
                    bundle.putString( "car$i", user.cars[i].toString())
                }
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = context?.let { UserDetailsListView(it) }!!

        val cars = mutableListOf<String>()
        val numCars = getData("numCars").toInt()
        if (numCars != 0) {
            for (i in 0 until numCars) {
               cars.add(i, "${getData("car$i")}\n")
            }
        }
        listAdapter.carList = cars
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileLayout =
            inflater.inflate(R.layout.fragment_user_profile, container,false)

        setTextV(profileLayout.user_name, "name")
        setTextV(profileLayout.address, "address")
        setTextV(profileLayout.numCars, "numCars")
        setTextV(profileLayout.userId, "id")

        profileLayout.addressLabel.text = getString(R.string.user_address)
        profileLayout.userCars.text = getString(R.string.user_cars)
        profileLayout.carLabel.text = getString(R.string.num_cars)
        profileLayout.userIdLabel.text = getString(R.string.user_id_label)

        profileLayout.lvCarItem.adapter = listAdapter
        return profileLayout
    }

    private fun getData (key: String) = arguments?.get(key).toString()

    private fun setTextV (v: TextView, key: String) {
        v.text = getData(key)
    }
}