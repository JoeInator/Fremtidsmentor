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
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_choose_role.*
import kotlinx.android.synthetic.main.fragment_choose_role.view.*
import com.labo.kaji.fragmentanimations.CubeAnimation
import com.labo.kaji.fragmentanimations.MoveAnimation
import android.view.animation.Animation


class FragmentChooseRole : Fragment() {

    private var nextFragment: Fragment = FragmentLogin()
    private lateinit var role_args: Bundle
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_choose_role, container,  false)

        role_args = Bundle()
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        view.move_on.isEnabled = false

        if (mPrefs.getBoolean("remember", false)) {
            val intent = Intent(requireContext(), ActivityCommunity::class.java).apply({})
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        view.mentee_btn.setOnClickListener {
            if (!view.mentee_btn.isSelected) {
                activity!!.main_layout.setBackground(resources.getDrawable(R.drawable.mentee))
                animation(view.mentee_btn)
                role_args.putString("userType", "users/normalUser")
                prefsEditor.putString("userType", "users/normalUser")
                nextFragment.setArguments(role_args)
                view.move_on.isEnabled = true
                view.mentee_btn.isSelected = true
                view.mentor_btn.isSelected = false
            }
        }

        view.mentor_btn.setOnClickListener {
            if (!view.mentor_btn.isSelected) {
                activity!!.main_layout.setBackground(resources.getDrawable(R.drawable.mentor))
                animation(view.mentor_btn)
                role_args.putString("userType", "users/mentor")
                prefsEditor.putString("userType", "users/mentor")
                nextFragment.setArguments(role_args)
                view.move_on.isEnabled = true
                view.mentor_btn.isSelected = true
                view.mentee_btn.isSelected = false
            }
        }

        view.move_on.setOnClickListener {
            if (view.mentee_btn.isSelected){
                prefsEditor.apply()
                prefsEditor.commit()
                toLogin()
            }
            else if (view.mentor_btn.isSelected) {
                Toast.makeText(requireContext(), resources.getString(R.string.Not_Implemented), Toast.LENGTH_SHORT).show()
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

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (!enter) {
            MoveAnimation.create(MoveAnimation.LEFT, enter, 1000)
        } else {
            CubeAnimation.create(CubeAnimation.LEFT, enter, 1000)
        }
    }

    private fun animation(view: View) {
        when (view.id) {
            R.id.mentor_btn -> {
                YoYo.with(Techniques.SlideInRight)
                    .duration(500)
                    .playOn(activity!!.findViewById(R.id.main_layout))
            }
            R.id.mentee_btn -> {
                YoYo.with(Techniques.SlideInLeft)
                    .duration(500)
                    .playOn(activity!!.findViewById(R.id.main_layout))
            }
        }
    }


}