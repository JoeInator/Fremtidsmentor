package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_community_user.view.*



class FragmentCommunity : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_community_user, container, false)

        view.MentorBtn.setOnClickListener {
            val newfragment = FragmentFilter()
            activity!!.supportFragmentManager.popBackStack()
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
            .replace(R.id.community_fragment, newfragment)
            .addToBackStack(null)
            .commit()
        }
        view.ProfilBtn.setOnClickListener{
            val newfragment = FragmentProfile()
            activity!!.supportFragmentManager.popBackStack()
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
            .replace(R.id.community_fragment, newfragment)
            .addToBackStack(null)
            .commit()
        }
        view.DiscoverBtn.setOnClickListener{
            val newfragment = FragmentDiscover()
            activity!!.supportFragmentManager.popBackStack()
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
            .replace(R.id.community_fragment, newfragment)
            .addToBackStack(null)
            .commit()
        }
//        view.UpgradeBtn.setOnClickListener{
//            notImplemented()
//        }
        view.UpgradeBtn.isEnabled = false
        view.SchedulesBtn.setOnClickListener{
            val newfragment = FragmentSchedules()
            activity!!.supportFragmentManager.popBackStack()
            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                .replace(R.id.community_fragment, newfragment)
                .addToBackStack(null)
                .commit()
        }
        /*
        In the middle goes all the code to be executed in the OnCreateView
         */

        return view
    }


    private fun notImplemented() {
        Toast.makeText(this.context, R.string.Not_Implemented, Toast.LENGTH_SHORT).show()
    }

}