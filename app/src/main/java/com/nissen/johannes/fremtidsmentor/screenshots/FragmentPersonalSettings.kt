package com.nissen.johannes.fremtidsmentor.screenshots

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson
import kotlinx.android.synthetic.main.fragment_personal_settings.view.*
import kotlinx.android.synthetic.main.personal_option_item.view.*

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_personal_settings, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        ref = FirebaseDatabase.getInstance().getReference("users/normalUser")
        Username = mPrefs.getString("name", "0")
        listView = view.findViewById(R.id.option_list)

        getUserInfoFromFirebase(Username)

        view.option_list.setOnItemClickListener { parent, view, position, id ->
            val next = optionsList[position]

            if (next.contains(resources.getString(R.string.email))) {
                nextFrag = FragmentChangeEmail()
                goToNextFrag(nextFrag)

            } else if (next.contains(resources.getString(R.string.name))) {
                nextFrag = FragmentChangeUsername()
                goToNextFrag(nextFrag)

            } else if (next.contains(resources.getString(R.string.password))) {
                nextFrag = FragmentChangePassword()
                goToNextFrag(nextFrag)

            } else if (next.contains(resources.getString(R.string.interests))) {
                notImplemented()

            } else if (next.contains(resources.getString(R.string.delete_account))) {
                deleteUser()

            }

        }

        return view
    }

    private fun getUserInfoFromFirebase(username: String) {

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireActivity(), R.string.firebaseError, Toast.LENGTH_LONG).show()
                activity!!.supportFragmentManager.popBackStack()
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (h in p0.children) {
                    if (h.child("username").getValue(String::class.java).equals(username)) {
                        operatingUser = h.getValue(NormalPerson::class.java)!!

                        Email = operatingUser.getEmail().toString().trim()
                        Password = operatingUser.getPassword().toString().trim()
                        Username = operatingUser.getUsername().toString().trim()
                    }
                }
            }
        })

        Handler().postDelayed({
         optionsList = arrayListOf<String>(
            resources.getString(R.string.email).plus(": ").plus(Email),
            resources.getString(R.string.name).plus(": ").plus(Username),
            resources.getString(R.string.password).plus(": ").plus(Password),
            resources.getString(R.string.interests),
            resources.getString(R.string.delete_account),
            resources.getString(R.string.empty_string))

            var adapter = Adapter(this.requireContext(), optionsList)
            adapter.notifyDataSetChanged()
            listView.adapter = adapter
        }, 500)
    }

    private fun goToNextFrag(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.community_fragment, fragment)
            .addToBackStack(null)
            .commit()
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
                prefsEditor.clear()
                val intent = Intent(requireContext(), ActivityMain::class.java).apply({})
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
    }

    private class Adapter (context: Context, private val Settings: ArrayList<String>): BaseAdapter() {

        private val mContext: Context

        init {
            mContext = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val main = layoutInflater.inflate(R.layout.personal_option_item, parent, false)
            val option = getItem(position) as String
            main.optionsBtn.text = option
            main.optionsBtn.id = getItemId(position).toInt()
            return main
        }

        override fun getItem(position: Int): Any {
            return Settings[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return Settings.size
        }

    }

}