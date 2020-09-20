package com.example.roomdb2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.stetho.Stetho


class MainActivity : AppCompatActivity(), OnLoadFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)
        Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_LONG).show()

        if (savedInstanceState == null ) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.root_layout, AddUserFragment(), getString(R.string.tag_add_user))
                    .commit()
            title = getString(R.string.title_create_user)
        }
    }

    override fun onLoadFragment() {
        loadFragment(
            UserListFragment(), getString(R.string.tag_user_list),
            getString(R.string.title_user_list)
        )
    }

    override fun onLoadFragmentWithArg(user: UserWithCars) {
        val fragment = UserProfileFragment.fragInstance(user)
        loadFragment(
            fragment, getString(R.string.tag_user_profile,),
            getString(R.string.title_user_details)
        )

    }

    private fun loadFragment (fragment: Fragment, tag: String, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_layout, fragment, tag)
            .addToBackStack(null)
            .commit()
        setTitle(title)
    }
}