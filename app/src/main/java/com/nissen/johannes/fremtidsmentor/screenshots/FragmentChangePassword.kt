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
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.fragment_change_username.view.*

class FragmentChangePassword: Fragment() {

    private lateinit var operatingUser: NormalPerson
    private lateinit var Username: String
    private lateinit var NewPassword: String
    private lateinit var Password: String
    private lateinit var oldUser: EditText
    private lateinit var newUser: EditText
    private lateinit var confirmBox: EditText

    private var userController = ControllerRegistry.usercontroller.UserController
    private var firebaseController = ControllerRegistry.databaseController.DatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_password, container, false)
        operatingUser = userController.getUser()!!
        Password = operatingUser.getPassword().toString().trim()
        Username = operatingUser.getUsername().toString().trim()

        oldUser = view.findViewById(R.id.oldPassword)
        newUser = view.findViewById(R.id.newPassword)
        confirmBox = view.findViewById(R.id.repeatPassword)

        view.changepassword.setOnClickListener {
            onClick()
        }
        return view
    }

    private fun onClick() {
        when (checkValidity()) {
            "approved" -> {
                operatingUser.setPassword(NewPassword)
                updateUser()
            }
            "Wrong Password" -> {
                Toast.makeText(requireContext(), R.string.wrong_password, Toast.LENGTH_SHORT).show()
            }
            "No Match" -> {
                Toast.makeText(requireContext(), R.string.new_passwords_not_matching, Toast.LENGTH_SHORT).show()
            }
            "Passwords are equal" -> {
                Toast.makeText(requireContext(), R.string.passwords_are_equal, Toast.LENGTH_SHORT).show()
            }
            "Password not confirmed" -> {
                Toast.makeText(requireContext(), R.string.password_not_confirmed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValidity(): String {
        val old = oldPassword.text.toString().trim()
        NewPassword = newPassword.text.toString().trim()
        val conf = repeatPassword.text.toString().trim()

        if (old == Password) {
            if (NewPassword != old) {
                if (conf.length > 0) {
                    if (conf == NewPassword) {
                        return "approved"
                    } else {
                        return "No Match"
                    }
                }
                else { return "Password not confirmed" }
            }
            else { return "Passwords are equal" }
        }
        else { return "Wrong Password" }

    }

    private fun updateUser() {
        userController.UpdateUser(operatingUser)
        firebaseController.updateUser(operatingUser)
        Log.d("SE HER", operatingUser.getUsername())
        Log.d("SE HER", userController.getUser()!!.getUsername())
        activity!!.supportFragmentManager.popBackStack()
    }

}