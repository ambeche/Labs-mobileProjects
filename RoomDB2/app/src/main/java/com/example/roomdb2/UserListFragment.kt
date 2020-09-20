package com.example.roomdb2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users_list.view.*

class UserListFragment : Fragment() {
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
        val activity = activity as Context
        val userAdapter = UserRecyclerViewAdapter(activityListener)
        val fragLayout = inflater.inflate(R.layout.fragment_users_list,container,false)
        fragLayout.rvUsers.layoutManager = LinearLayoutManager(activity)
        fragLayout.rvUsers.adapter = userAdapter

        val userVM = ViewModelProvider(this).get(UserViewModel::class.java)
        userVM.userData.observe(viewLifecycleOwner, Observer{userdata ->
                        userAdapter.setUserData(userdata)
        })
        return fragLayout
    }
}
