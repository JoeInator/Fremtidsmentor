package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_change_username.view.*

class FragmentChangeUsername: Fragment() {

    private lateinit var operatingUser: NormalPerson
    private lateinit var Username: String
    private lateinit var newUsername: String
    private lateinit var Password: String
    private lateinit var oldUser: EditText
    private lateinit var newUser: EditText
    private lateinit var confirmBox: EditText

    private var userController = ControllerRegistry.usercontroller.UserController
    private var firebaseController = ControllerRegistry.databaseController.DatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_username, container, false)
        operatingUser = userController.getUser()!!

        Password = operatingUser.getPassword().toString().trim()
        Username = operatingUser.getUsername().toString().trim()

        oldUser = view.findViewById(R.id.oldName)
        newUser = view.findViewById(R.id.newName)
        confirmBox = view.findViewById(R.id.confirmName)

        view.changeUserBtn.setOnClickListener {
            onClick()
        }
        return view
    }

    private fun onClick() {
        when (checkValidity()) {
            "approved" -> {
                operatingUser.setUsername(newUsername)
                updateUser()
            }
            "Wrong Password" -> {
                Toast.makeText(requireContext(), R.string.wrong_password, Toast.LENGTH_SHORT).show()
            }
            "Wrong Username" -> {
                Toast.makeText(requireContext(), R.string.wrong_username, Toast.LENGTH_SHORT).show()
            }
            "Username are equal" -> {
                Toast.makeText(requireContext(), R.string.username_are_equal, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValidity(): String {
        val old = oldUser.text.toString().trim()
        newUsername = newUser.text.toString().trim()
        val conf = confirmBox.text.toString().trim()

        if (old == Username) {
            if (newUsername != old) {
                if (conf == Password) {
                    return "approved"
                }
                else { return "Wrong Password" }
            }
            else { return "Username are equal" }
        }
        else { return "Wrong Username" }
    }

    private fun updateUser() {
        userController.UpdateUser(operatingUser)
        firebaseController.updateUser(operatingUser)
        Log.d("SE HER", operatingUser.getUsername())
        Log.d("SE HER", userController.getUser()!!.getUsername())
        activity!!.supportFragmentManager.popBackStack()
    }

}