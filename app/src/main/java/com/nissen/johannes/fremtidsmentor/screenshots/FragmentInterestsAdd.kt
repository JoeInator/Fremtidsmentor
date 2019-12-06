package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.renderscript.Sampler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.labo.kaji.fragmentanimations.MoveAnimation
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_add_interests.view.*
import com.nissen.johannes.fremtidsmentor.adapters.AllInterestsAdapter
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.entities.NormalPerson


class FragmentInterestsAdd: Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var Interests: ArrayList<String>
    private lateinit var checkedInterests: ArrayList<String>
    private lateinit var operatingUser: NormalPerson

    private var userController = ControllerRegistry.usercontroller.UserController
    private var firebaseController = ControllerRegistry.databaseController.DatabaseController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_interests, container, false)
        operatingUser = userController.getUser()!!
        checkedInterests = ArrayList<String>()

        //Loading users current interests
        checkedInterests = userController.getUser()!!.getInterests()!!

        view.button.setOnClickListener {
            onConfirm()
        }

        /**
         * loads a path in the Firebase Database
         */
        ref = FirebaseDatabase.getInstance().getReference("interests")

        view.setBackgroundColor(resources.getColor(R.color.semiTransWhite))
        loadListOfPossibleInterests(view)

        return view
    }

    private fun onConfirm() {
        operatingUser.setInterests(checkedInterests)
        userController.UpdateUser(operatingUser)
        firebaseController.updateUser(operatingUser)
        Log.d("SE HER", operatingUser.getUsername())
        Log.d("SE HER", userController.getUser()!!.getUsername())
        activity!!.supportFragmentManager.popBackStack()
    }

    //Loading all the possible interests
    fun loadListOfPossibleInterests(view: View) {
        Interests = ArrayList<String>()

        //Loading all the possible interests from firebase and add them to the list shown
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.children) {
                    for (j in h.children) {
                        Interests.add(j.value.toString())
                    }
                }
                view.all_interests_possible.setLayoutManager(LinearLayoutManager(requireContext()))
                view.all_interests_possible.adapter = AllInterestsAdapter(requireContext(), Interests, checkedInterests)
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), resources.getString(R.string.firebaseError), Toast.LENGTH_SHORT).show()
                activity!!.supportFragmentManager.popBackStack()
            }
        })
    }

    //Animation on enter and exit
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (!enter) {
            MoveAnimation.create(MoveAnimation.DOWN, enter, 250)
        } else {
            MoveAnimation.create(MoveAnimation.UP, enter, 250)
        }
    }

}