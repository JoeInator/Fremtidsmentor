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
import kotlinx.android.synthetic.main.fragment_change_email.view.*

class FragmentChangeEmail: Fragment() {
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference
    private lateinit var operatingUser: NormalPerson
    private lateinit var Username: String
    private lateinit var Email: String
    private lateinit var NewEmail: String
    private lateinit var Password: String
    private lateinit var oldMail: EditText
    private lateinit var newMail: EditText
    private lateinit var confirmMail: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_email, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        Username = mPrefs.getString("name", "0")
        ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        getUserInfoFromFirebase(Username)

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
                updateUser(operatingUser.getId()!!, NewEmail)
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
                        Email = operatingUser.getEmail().toString().trim()
                    }
                }
            }
        })
    }

    private fun updateUser(key: String, newEmail: String) {

        operatingUser.setUsername(newEmail)

        Username = newEmail
        ref.child(key).child("email").setValue(newEmail).addOnCompleteListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

}