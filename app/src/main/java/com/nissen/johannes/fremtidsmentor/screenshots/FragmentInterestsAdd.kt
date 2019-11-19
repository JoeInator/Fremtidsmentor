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
        ref = FirebaseDatabase.getInstance().getReference("interests")
        pushref = FirebaseDatabase.getInstance().getReference(mPrefs.getString("userType",""))

        view.setBackgroundColor(resources.getColor(R.color.semiTransWhite))
        loadListOfPossibleInterests(view)

        return view
    }

    fun loadListOfPossibleInterests(view: View) {
        Interests = ArrayList<String>()
        checkedInterests = ArrayList<String>()

        pushref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val h = p0.child(mPrefs.getString("userID","").plus("/interests"))
                for (j in h.children) {
                    checkedInterests.add(j.value.toString())
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.children) {
                    for (j in h.children) {
                        Interests.add(j.value.toString())
                    }
                }
                view.all_interests_possible.setLayoutManager(LinearLayoutManager(requireContext()))
                view.all_interests_possible.adapter = ListeelemAdapter()
                for (h in Interests) {
                    Log.d("INTERESTS", h)
                }
                for (h in checkedInterests) {
                    Log.d("CHECKEDINTERESTS", h)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (!enter) {
            MoveAnimation.create(MoveAnimation.DOWN, enter, 250)
        } else {
            MoveAnimation.create(MoveAnimation.UP, enter, 250)
        }
    }

    internal inner class ListeelemAdapter : RecyclerView.Adapter<ListeelemViewholder>() {
        override fun getItemCount(): Int {
            return Interests.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeelemViewholder {
            val listeelementViews =
                layoutInflater.inflate(R.layout.list_all_interests_possible_item, parent, false)
            return ListeelemViewholder(listeelementViews)
        }

        override fun onBindViewHolder(vh: ListeelemViewholder, position: Int) {
            val item = Interests[position]
            vh.checkBox.text = item

            //in some cases, it will prevent unwanted situations
            vh.checkBox.setOnCheckedChangeListener(null)

            vh.checkBox.isChecked = checkedInterests.contains(item)

            vh.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    //set your object's last status
                    vh.checkBox.setSelected(isChecked)
                    if (isChecked) {
                        checkedInterests.add(vh.checkBox.text.toString())
                    } else {
                        checkedInterests.remove(vh.checkBox.text.toString())
                    }
                    Log.d("TAG","BULLER".plus(checkedInterests.toString()))
                }
            })
        }
    }

    internal inner class ListeelemViewholder: RecyclerView.ViewHolder {
        var checkBox: CheckBox

        constructor(listeelementViews: View) : super(listeelementViews) {
            checkBox = listeelementViews.findViewById(R.id.checkBox)
//            checkBox.setOnClickListener {
//                if (checkBox.isChecked) {
//                    checkedInterests.add(checkBox.text.toString())
//                } else {
//                    checkedInterests.remove(checkBox.text.toString())
//                }
////                Log.d("TAG","BULLER".plus(checkedInterests.toString()))
//            }
        }
    }
}