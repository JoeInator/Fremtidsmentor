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
import kotlinx.android.synthetic.main.fragment_change_email.view.*

class FragmentChangeEmail: Fragment() {
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference
    private lateinit var operatingUser: NormalPerson
//    private lateinit var Username: String
    private lateinit var Email: String
    private lateinit var NewEmail: String
    private lateinit var Password: String
    private lateinit var oldMail: EditText
    private lateinit var newMail: EditText
    private lateinit var confirmMail: EditText

    private var userController = ControllerRegistry.usercontroller.UserController
    private var firebaseController = ControllerRegistry.databaseController.DatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_email, container, false)
        operatingUser = userController.getUser()!!
        Password = operatingUser.getPassword().toString().trim()
        Email = operatingUser.getEmail().toString().trim()

        oldMail = view.findViewById(R.id.oldMail)
        newMail = view.findViewById(R.id.newMail)
        confirmMail = view.findViewById(R.id.confirmMail)

        view.changeEmail.setOnClickListener {
            onClick()
        }
        return view
    }

    private fun onClick() {
        when (checkValidity()) {
            "approved" -> {
                operatingUser.setEmail(NewEmail)
                updateUser()
            }
            "Wrong Password" -> {
                Toast.makeText(requireContext(), R.string.wrong_password, Toast.LENGTH_SHORT).show()
            }
            "Wrong Email" -> {
                Toast.makeText(requireContext(), R.string.wrong_email, Toast.LENGTH_SHORT).show()
            }
            "Emails are equal" -> {
                Toast.makeText(requireContext(), R.string.emails_are_the_same, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkValidity(): String {
        val old = oldMail.text.toString().trim()
        NewEmail= newMail.text.toString().trim()
        val conf = confirmMail.text.toString().trim()

        if (old == Email) {
            if (NewEmail != old) {
                if (conf == Password) {
                    return "approved"
                }
                else { return "Wrong Password" }
            }
            else { return "Emails are equal" }
        }
        else { return "Wrong Email" }
    }

    private fun updateUser() {
        userController.UpdateUser(operatingUser)
        firebaseController.updateUser(operatingUser)
        Log.d("SE HER", operatingUser.getUsername())
        Log.d("SE HER", userController.getUser()!!.getUsername())
        activity!!.supportFragmentManager.popBackStack()
    }

}