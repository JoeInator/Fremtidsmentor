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
import kotlinx.android.synthetic.main.fragment_change_username.view.*

class FragmentChangeUsername: Fragment() {

    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference
    private lateinit var operatingUser: NormalPerson
    private lateinit var FirebaseID: String
    private lateinit var Username: String
    private lateinit var newUsername: String
    private lateinit var Password: String
    private lateinit var oldUser: EditText
    private lateinit var newUser: EditText
    private lateinit var confirmBox: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_change_username, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        Username = mPrefs.getString("name", "0")
        ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        getUserInfoFromFirebase(Username)

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
                updateUser(operatingUser.getId()!!, newUsername)
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

    private fun updateUser(key: String, newUser: String) {

        operatingUser.setUsername(newUser)

        Username = newUser
        ref.child(key).child("username").setValue(newUser).addOnCompleteListener {
            prefsEditor.putString("name", newUser)
            activity!!.supportFragmentManager.popBackStack()
        }
    }

}