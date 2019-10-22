package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.view.*
import kotlinx.android.synthetic.main.fragment_change_username.view.*

class FragmentChangePassword: Fragment() {

    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference
    private lateinit var operatingUser: NormalPerson
    private lateinit var Username: String
    private lateinit var NewPassword: String
    private lateinit var Password: String
    private lateinit var oldUser: EditText
    private lateinit var newUser: EditText
    private lateinit var confirmBox: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_password, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        Username = mPrefs.getString("name", "0")
        ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        getUserInfoFromFirebase(Username)

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
                updateUser(operatingUser.getId()!!, NewPassword)
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
        }
    }

    private fun checkValidity(): String {
        val old = oldPassword.text.toString().trim()
        NewPassword = newPassword.text.toString().trim()
        val conf = repeatPassword.text.toString().trim()

        if (old == Password) {
            if (NewPassword != old) {
                if (conf == NewPassword) {
                    return "approved"
                }
                else { return "No Match" }
            }
            else { return "Passwords are equal" }
        }
        else { return "Wrong Password" }

    }

    private fun getUserInfoFromFirebase(username: String) {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireActivity(), R.string.firebaseError, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (h in p0.children) {
                    if (h.child("username").getValue(String::class.java).equals(username)) {
                        operatingUser = h.getValue(NormalPerson::class.java)!!

                        Password = operatingUser.getPassword().toString().trim()
                        Username = operatingUser.getUsername().toString().trim()

                    }
                }
            }
        })
    }

    private fun updateUser(key: String, newPassword: String) {

        operatingUser.setUsername(newPassword)

        Username = newPassword
        ref.child(key).child("password").setValue(newPassword).addOnCompleteListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

}