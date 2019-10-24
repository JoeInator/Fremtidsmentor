package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button

import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.ArrayList


class FragmentProfile : Fragment() {

    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Inflate the view
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()

        view.personal_info_botton.setOnClickListener {
            val newfragment = FragmentPersonalSettings()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.community_fragment, newfragment)
                .addToBackStack(null)
                .commit()
        }

        view.logoutBtn.setOnClickListener {
            prefsEditor.clear().commit()
            val intent = Intent(requireContext(), ActivityMain::class.java).apply({})
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return view
    }

}
