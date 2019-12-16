package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_profile.view.*


class FragmentProfile : Fragment() {

    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cv_foto)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedBitmapDrawable.isCircular = true
        view.profileImg.setImageDrawable(roundedBitmapDrawable)
        view.Basbasic_info_text.text = mPrefs.getString("name", "0")

        view.personal_info_botton.text = "       ".plus(resources.getString(R.string.personal_info))
        view.personal_info_botton.setOnClickListener {
            val newfragment = FragmentPersonalSettings()
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                .replace(R.id.community_fragment, newfragment)
                .addToBackStack(null)
                .commit()
        }

        view.logoutBtn.text = "       ".plus(resources.getString(R.string.logout))
        view.logoutBtn.setOnClickListener {
            prefsEditor.clear().commit()
            val intent = Intent(requireContext(), ActivityMain::class.java).apply({})
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        view.subscription_button.text = "       ".plus(resources.getString(R.string.subscribe))
        view.subscription_button.setOnClickListener {
            val newfragment = FragmentSubscriptions()
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                .replace(R.id.community_fragment, newfragment)
                .addToBackStack(null)
                .commit()
        }

        view.notifocation_botton.text = "       ".plus(resources.getString(R.string.notifications))
        view.notifocation_botton.setOnClickListener {
            view.switch1.toggle()
        }

        return view
    }

}
