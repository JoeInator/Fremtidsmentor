package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.labo.kaji.fragmentanimations.CubeAnimation
import com.labo.kaji.fragmentanimations.MoveAnimation
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class FragmentLogin : Fragment() {

    private lateinit var Username: String
    private lateinit var Password: String
    private var user: NormalPerson? = null
    private var userApproved: Boolean = false
    private var path: String = "user/normalUser"
    private lateinit var remember_me: CheckBox
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        path = "users/normalUser"//arguments!!.getString("userType")
        ref = FirebaseDatabase.getInstance().getReference(path)
        remember_me = view.findViewById<CheckBox>(R.id.remember_me)
        view.UserName_text.setText("Test123")
        view.UserName_text.maxLines = 1
        view.passwordText.setText("Test321")


        prefsEditor.clear().commit()
        view.loginBtn.setOnClickListener {
            Login()
        }

        view.sign_up_option.setOnClickListener {
            signUp()
        }

        view.forgot_password.setOnClickListener {
            Toast.makeText(this.context, R.string.Not_Implemented, Toast.LENGTH_SHORT).show()
        }

        view.sign_up_option.getPaint().setUnderlineText(true)

        view.forgot_password.getPaint().setUnderlineText(true)
        return view
    }

    private fun Login() {
            Username = UserName_text.text.toString().trim()
            Password = passwordText.text.toString().trim()
            getFirebaseUser(Username, Password)
    }

    private fun getFirebaseUser(username: String, password: String) {

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), R.string.firebaseError, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (h in p0.children) {
                    if (h.child("username").getValue(String::class.java).equals(username)
                        and h.child("password").getValue(String::class.java).equals(password)) {
                            val userID = h.child("id").value.toString()

                            userApproved = true

                            if (remember_me.isChecked) { prefsEditor.putBoolean("remember", true) }
                            else { prefsEditor.putBoolean("remember", false) }

                            prefsEditor.putString("name", Username)
                            prefsEditor.putString("userID", userID)
                            prefsEditor.putString("userType", path)
                            prefsEditor.apply()
                            prefsEditor.commit()
                            if (isAdded) {
                                Log.d("DIE!!", "Does login start the intent??")
                                val intent = Intent(requireContext(), ActivityCommunity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                Log.d("DIE!!", "Does login start the intent??")
                            }
                    }
                }

                if (!userApproved) {
                    if (isAdded) {
                        Toast.makeText(requireContext(), R.string.invalid_user, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

        })
    }

    private fun signUp() {
        val newfragment = FragmentSignUp()
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.login_fragment, newfragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

//    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
////        return MoveAnimation.create(MoveAnimation.LEFT, enter, 1000)
////    }


}