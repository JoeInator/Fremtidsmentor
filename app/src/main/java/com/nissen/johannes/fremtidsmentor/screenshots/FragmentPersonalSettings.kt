package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_personal_settings.*
import kotlinx.android.synthetic.main.fragment_profile.*

class FragmentPersonalSettings: Fragment() {

    private lateinit var Username: String
    private lateinit var Email: String
    private lateinit var Password: String
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference

    private lateinit var operatingUser: NormalPerson

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_personal_settings, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        Username = mPrefs.getString("name", "0")

        getUserInfoFromFirebase(Username)



        return view
    }

    private fun getUserInfoFromFirebase(username: String) {

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), R.string.firebaseError, Toast.LENGTH_LONG).show()
                activity!!.supportFragmentManager.popBackStack()
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (h in p0.children) {
                    if (h.child("username").getValue(String::class.java).equals(username)) {
                        operatingUser = h.getValue(NormalPerson::class.java)!!

                        Email = operatingUser.getEmail().toString().trim()
                        Password = operatingUser.getPassword().toString().trim()
                        Username = operatingUser.getUsername().toString().trim()

                        emailBtn.text = String.format("%s: %s", resources.getString(R.string.email), Email)
                        usernameBtn.text = String.format("%s: %s", resources.getString(R.string.name), Username)
                        passwordBtn.text = String.format("%s: %s", resources.getString(R.string.password), Password)
                    }
                }
            }
        })



    }


}