package com.nissen.johannes.fremtidsmentor.screenshots

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.adapters.PersonalSettingsAdapter
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_personal_settings.view.*

class FragmentPersonalSettings: Fragment() {

    private lateinit var Username: String
    private lateinit var Email: String
    private lateinit var Password: String
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var ref: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var nextFrag: Fragment
    private lateinit var operatingUser: NormalPerson
    private lateinit var optionsList: ArrayList<String>

    var userController = ControllerRegistry.usercontroller.UserController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_personal_settings, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        Username = mPrefs.getString("name", "0")
        listView = view.findViewById(R.id.option_list)

//        getUserInfoFromFirebase(Username, requireContext())
        operatingUser = userController.getUser()!!

        Email = operatingUser.getEmail().toString().trim()
        Password = operatingUser.getPassword().toString().trim()
        Username = operatingUser.getUsername().toString().trim()

        if (isAdded) {
            optionsList = arrayListOf<String>(
                resources.getString(R.string.email).plus(": ").plus(Email),
                resources.getString(R.string.name).plus(": ").plus(Username),
                resources.getString(R.string.password).plus(": ").plus(Password),
                resources.getString(R.string.interests),
                resources.getString(R.string.delete_account)
                //resources.getString(R.string.empty_string)
            )
            var adapter = PersonalSettingsAdapter(requireContext(), optionsList)
            adapter.notifyDataSetChanged()
            listView.adapter = adapter
        }

        view.option_list.setOnItemClickListener { parent, view, position, id ->
            val next = optionsList[position]

            if (next.contains(resources.getString(R.string.email))) {
                nextFrag = FragmentChangeEmail()
                if (!Username.equals("Test123")) {
                    goToNextFrag(nextFrag)
                } else {
                    notAvailable()
                }

            } else if (next.contains(resources.getString(R.string.name))) {
                nextFrag = FragmentChangeUsername()
                if (!Username.equals("Test123")) {
                    goToNextFrag(nextFrag)
                } else {
                    notAvailable()
                }

            } else if (next.contains(resources.getString(R.string.password))) {
                nextFrag = FragmentChangePassword()
                if (!Username.equals("Test123")) {
                    goToNextFrag(nextFrag)
                } else {
                    notAvailable()
                }

            } else if (next.contains(resources.getString(R.string.interests))) {
                nextFrag = FragmentInterests()
                    goToNextFrag(nextFrag)

            } else if (next.contains(resources.getString(R.string.delete_account))) {
                if (!Username.equals("Test123")) {
                    deleteUser()
                } else {
                    notAvailable()
                }
            }
        }

        return view
    }

    private fun goToNextFrag(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
            .replace(R.id.community_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun notAvailable() {
        Toast.makeText(requireContext(), "Sevice not available on Test account", Toast.LENGTH_SHORT).show()
    }

    private fun notImplemented() {
        Toast.makeText(this.context, R.string.Not_Implemented, Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_account)
            .setMessage(R.string.confirm_delete)
            .setCancelable(true)
            .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener {
                    dialog, id -> confirmed()
            })

            .setNegativeButton(android.R.string.no, DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
            .show()
    }

    private fun confirmed() {
        ref.child(operatingUser.getId().toString()).removeValue()
            .addOnCompleteListener {
                prefsEditor.clear().commit()
                val intent = Intent(requireContext(), ActivityMain::class.java).apply({})
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
    }
}