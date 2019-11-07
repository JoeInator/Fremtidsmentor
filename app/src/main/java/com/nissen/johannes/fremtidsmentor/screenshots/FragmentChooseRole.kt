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
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_choose_role.*
import kotlinx.android.synthetic.main.fragment_choose_role.view.*

class FragmentChooseRole : Fragment() {

    private var nextFragment: Fragment = FragmentLogin()
    private lateinit var role_args: Bundle
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_choose_role, container,  false)

        role_args = Bundle()
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        if (mPrefs.getBoolean("remember", false)) {
            val intent = Intent(requireContext(), ActivityCommunity::class.java).apply({})
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        view.mentee_btn.setOnClickListener {
            role_args.putString("userType","users/normalUser")
            nextFragment.setArguments(role_args)
            view.mentee_btn.isSelected = true
            view.mentor_btn.isSelected = false
        }

        view.mentor_btn.setOnClickListener {
            role_args.putString("userType","users/mentor")
            nextFragment.setArguments(role_args)
            view.mentor_btn.isSelected = true
            view.mentee_btn.isSelected = false
        }

        view.move_on.setOnClickListener {
            if (view.mentee_btn.isSelected){
                toLogin()
            }
            else if (view.mentor_btn.isSelected) {
                Toast.makeText(requireContext(), resources.getString(R.string.Not_Implemented), Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), resources.getString(R.string.role_not_chosen), Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun toLogin() {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.login_fragment, nextFragment)
            .addToBackStack(null)
            .commit()
    }


}