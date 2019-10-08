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
            val newfragment = FragmentMentors()
            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.community_fragment, newfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        view.ProfilBtn.setOnClickListener{
            val newfragment = FragmentProfile()
            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.community_fragment, newfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        view.DiscoverBtn.setOnClickListener{
            val newfragment = FragmentDiscover()
            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.community_fragment, newfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        view.UpgradeBtn.setOnClickListener{
            notImplemented()
        }
        view.SETTINGS_Btn.setOnClickListener{
            notImplemented()
        }
        /*
        In the middle goes all the code to be executed in the OnCreateView
         */

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



    private fun notImplemented() {
        Toast.makeText(this.context, "Ikke implementeret", Toast.LENGTH_SHORT).show()
    }

}