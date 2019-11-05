package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_choose_role, container,  false)

        role_args = Bundle()

        view.mentee_btn.setOnClickListener {
            role_args.putString("userType","users/normalUser")
            nextFragment.setArguments(role_args)
            Toast.makeText(requireContext(), role_args.getCharSequence("userType"), Toast.LENGTH_SHORT).show()
            view.mentee_btn.isSelected = true
            view.mentor_btn.isSelected = false
        }

        view.mentor_btn.setOnClickListener {
            role_args.putString("userType","users/mentor")
            nextFragment.setArguments(role_args)
            Toast.makeText(requireContext(), role_args.getCharSequence("userType"), Toast.LENGTH_SHORT).show()
            view.mentor_btn.isSelected = true
            view.mentee_btn.isSelected = false
        }

        view.move_on.setOnClickListener {
            toLogin()
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