package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.adapters.InterestsAdapter
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import kotlinx.android.synthetic.main.fragment_list_of_interests.view.*

class FragmentInterests: Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var Interests: ArrayList<String>

    var userController = ControllerRegistry.usercontroller.UserController
    private lateinit var Uid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_of_interests, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        Uid = userController.getUser()!!.getId()!!
        ref = FirebaseDatabase.getInstance().getReference(mPrefs.getString("userType",""))

        Interests = userController.getUser()!!.getInterests()!!
        if (!Interests.isEmpty()) {
            view.list_interests.setLayoutManager(LinearLayoutManager(requireContext()))
            view.list_interests.adapter = InterestsAdapter(requireContext(), Interests)
        } else {
            view.no_interests.visibility = View.VISIBLE
        }

        view.add_interests.setOnClickListener {
            val nextFrag = FragmentInterestsAdd()
            val args = Bundle()

            //Sending current intersts to the next fragment
            args.putStringArrayList("currentINTS", Interests)
            nextFrag.arguments = args

            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                .replace(R.id.community_fragment, nextFrag)
                .addToBackStack(null)
                .commit()
        }

        return view
    }


}