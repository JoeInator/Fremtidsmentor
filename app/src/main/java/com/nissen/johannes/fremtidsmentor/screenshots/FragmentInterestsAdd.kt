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
import android.widget.CompoundButton
import com.nissen.johannes.fremtidsmentor.adapters.AllInterestsAdapter
import kotlinx.android.synthetic.main.list_all_interests_possible_item.*


class FragmentInterestsAdd: Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var pushref: DatabaseReference
    private lateinit var mPrefs: SharedPreferences
    private lateinit var Uid: String
    private lateinit var Interests: ArrayList<String>
    private lateinit var checkedInterests: ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_interests, container, false)

        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        Uid = mPrefs.getString("userID","")
        checkedInterests = ArrayList<String>()

        //Loading users current interests
        checkedInterests = arguments!!.getStringArrayList("currentINTS")

        view.button.setOnClickListener {
            onConfirm()
        }

        /**
         * Defines two paths in the Firebase Database
         */
        ref = FirebaseDatabase.getInstance().getReference("interests")
        pushref = FirebaseDatabase.getInstance().getReference(mPrefs.getString("userType",""))

        view.setBackgroundColor(resources.getColor(R.color.semiTransWhite))
        loadListOfPossibleInterests(view)

        return view
    }

    private fun onConfirm() {
        pushref.child(mPrefs.getString("userID","").plus("/interests")).removeValue()

        pushref.child(mPrefs.getString("userID","").plus("/interests")).setValue(checkedInterests)
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "SUCCES!!", Toast.LENGTH_SHORT).show()
                activity!!.supportFragmentManager.popBackStack()
            }
            .addOnCanceledListener {
                Toast.makeText(requireContext(), resources.getString(R.string.firebaseError), Toast.LENGTH_SHORT).show()
            }

    }

    //Loading all the possible interests
    fun loadListOfPossibleInterests(view: View) {
        Interests = ArrayList<String>()

        //Loading both all the possible interests
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