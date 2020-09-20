package com.example.roomdb2
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_add_user.view.*

class AddUserFragment: Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var activityListener: OnLoadFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoadFragment) {
            activityListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragLayout = inflater.inflate(R.layout.fragment_add_user,container,false)
        userViewModel.userData.observe(viewLifecycleOwner, {
            val names = it.map { userWithCars -> userWithCars.user.lastName }

            //setting adapter for material exposed dropdown menu
            val dropDownMenuAdapter = ArrayAdapter(requireContext(), R.layout.list_item, names)
            (fragLayout.evOwner.editText as? AutoCompleteTextView)?.setAdapter(dropDownMenuAdapter)
        })

        fragLayout.btnAddUser.setOnClickListener(ClickListener())
        fragLayout.btnAddCar.setOnClickListener(ClickListener())
        fragLayout.btnOpenList.setOnClickListener(ClickListener())

        return fragLayout
    }

    private fun addUserToDB () {
       if (isNotEmptyTV(evFirstName, evLastName, evStreet, evHouse, evPostal, evCity)){
           val address = UserAddress(getInput(evHouse),getInput(evStreet),
               getInput(evCity), getInput(evPostal).toLong())

           userViewModel.addUser(User(0, getInput(evFirstName),
               getInput(evLastName), address))

           setNotice(getString(R.string.user), getInput(evLastName))
           clearTxt(evFirstName, evLastName, evStreet, evHouse, evPostal, evCity)
       }else toast(getString(R.string.failed_to_create))
    }

    private fun addCarToDB() {
        if (isNotEmptyTV(evColor, evMake, evModel, evValue, evOwner, evEngine)) {

            val ownerId = userViewModel.getCarOwnerId(getInput(evOwner)) ?: 0
            userViewModel.addCar(Car(0, ownerId, getInput(evMake),
                getInput(evModel), getInput(evEngine), getInput(evColor),
                getInput(evValue).toInt()))
            setNotice(getString(R.string.car), getInput(evModel))
            clearTxt(evColor, evMake, evModel, evValue, evOwner, evEngine)

        }else toast(getString(R.string.failed_to_create))
    }

    inner class ClickListener : View.OnClickListener{
        override fun onClick(v: View?) {
            when(v?.id) {
                 R.id.btnAddUser -> addUserToDB()
                 R.id.btnAddCar -> addCarToDB()
                 R.id.btnOpenList -> activityListener.onLoadFragment()
            }
        }
    }
    private fun getInput(v: TextInputLayout) = v.editText?.text.toString()

    private fun clearTxt(vararg v: TextInputLayout) {
        val ev = listOf(*v)
        ev.forEach { it.editText?.text?.clear() }
    }

    private fun isNotEmptyTV(vararg v: TextInputLayout): Boolean {
        val ev = listOf(*v)
       return ev.run {
           this.none { inputTxt -> inputTxt.editText!!.text.isEmpty() }
       }
    }

    private fun setNotice (tag: String, name: String) {
        tvNotice.visibility = View.VISIBLE
        tvNotice.text = getString(R.string.notice, tag, name)
    }

    private fun toast(string:String){
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }
}