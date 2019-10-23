package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.view.*


class FragmentSignUp : Fragment() {

    lateinit var mPrefs: SharedPreferences
    lateinit var prefsEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_signup, container, false)

        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()

        view.signup_btn.setOnClickListener{
            onClick()
        }

        return view
    }

    private fun onClick() {
        when(checkContent()) {
            "password not the same" -> Toast.makeText(this.context, R.string.noMatchOnPassword, Toast.LENGTH_SHORT).show()
            "not all are filled" -> Toast.makeText(this.context, R.string.fillEntireForm, Toast.LENGTH_SHORT).show()
            "approved" -> nextAct()
        }
    }

    private fun checkContent(): String {

        if (!signup_typepassword.text.toString().equals(signup_confpassword.text.toString()))
            return "password not the same"

        if(!signup_username.text.toString().equals("")){
            if(!signup_email.text.toString().equals("")) {
             if(!signup_typepassword.text.toString().equals("")){
                 if (!signup_confpassword.text.toString().equals(""))
                     return "approved"
             }
            }

        }
        return "not all are filled"
    }
    private fun nextAct() {
        saveUser()
        val intent = Intent(this.context, ActivityCommunity::class.java).apply({})
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun saveUser() {

        val username = signup_username.text.toString().trim()
        val email = signup_email.text.toString().trim()
        val password = signup_typepassword.text.toString().trim()
        val interests : ArrayList<String> = arrayListOf("hjkl", "fghjklæ")

        val ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        val keyInt = ref.push().getKey()
        val newUser = NormalPerson(keyInt!!, username, email, password, interests)

        ref.child(keyInt!!).setValue(newUser).addOnCompleteListener {
            prefsEditor.putString("name", newUser!!.getUsername())
            prefsEditor.apply()
            prefsEditor.commit()
        }

    }

}