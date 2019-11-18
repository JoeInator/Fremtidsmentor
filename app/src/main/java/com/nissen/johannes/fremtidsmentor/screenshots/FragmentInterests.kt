package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_list_of_interests.view.*

class FragmentInterests: Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var mPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private lateinit var Interests: ArrayList<String>

    private lateinit var Uid: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_of_interests, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        Uid = mPrefs.getString("userID","")
        ref = FirebaseDatabase.getInstance().getReference(mPrefs.getString("userType",""))
        Toast.makeText(requireContext(),mPrefs.getString("userID","Der er en fejl"),Toast.LENGTH_SHORT).show()
        acquireComps(view)

        view.add_interests.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.community_fragment, FragmentInterestsAdd())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun acquireComps(view: View) {
        Interests = ArrayList<String>()

        /*
        val gson = Gson()
        val jsonText = mPrefs.getString("interests", "")
        val text = gson.fromJson(jsonText, ArrayList::class.java)
         */

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.child(Uid.plus("/interests")).children) {
                    val interest = h.value.toString()
                    Interests.add(interest)
                }
                view.list_interests.setLayoutManager(LinearLayoutManager(requireContext()))
                view.list_interests.adapter = ListeelemAdapter()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    internal inner class ListeelemAdapter : RecyclerView.Adapter<ListeelemViewholder>() {
        override fun getItemCount(): Int {
            return Interests.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeelemViewholder {
            val listeelementViews =
                layoutInflater.inflate(R.layout.list_interests_item, parent, false)
            return ListeelemViewholder(listeelementViews)
        }

        override fun onBindViewHolder(vh: ListeelemViewholder, position: Int) {
            vh.interestsTextView.text = "   ".plus(Interests[position])
            if (position == Interests.lastIndex){ vh.separetor.visibility = View.VISIBLE }
            Toast.makeText(requireContext(), itemCount.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    internal inner class ListeelemViewholder: RecyclerView.ViewHolder {

        var interestsTextView: TextView
        var separetor: ImageView

        constructor(listeelementViews: View) : super(listeelementViews) {
            interestsTextView = listeelementViews.findViewById(R.id.interest_view)
            separetor = listeelementViews.findViewById(R.id.separetor)

        }

    }

}