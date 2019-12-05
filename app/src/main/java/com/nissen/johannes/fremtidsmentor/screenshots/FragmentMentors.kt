package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.adapters.MentorListAdapter
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.main.fragment_chosen_mentor.view.*
import kotlinx.android.synthetic.main.list_mentor_tem.view.*

class FragmentMentors : Fragment() {

    private val mentorsInList = arrayListOf<Mentor>()

    lateinit var ref: DatabaseReference
    lateinit var listView: ListView
    lateinit var category: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mentor, container, false)

        ref = FirebaseDatabase.getInstance().getReference("users/mentor")
        listView = view.findViewById<ListView>(R.id.mentor_list)
        category = arguments!!.getString("Filter")

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedMentor = FragmentChosenMentor()

            val pdf_args = Bundle()

            pdf_args.putString("name", mentorsInList.get(position).getUsername())
            selectedMentor.setArguments(pdf_args)

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.community_fragment, selectedMentor)
                .addToBackStack(null)
                .commit()
        }

        getFirebaseMentors()

        return view
    }

    private fun getFirebaseMentors() {
        var adapter = MentorListAdapter(this.requireContext(), mentorsInList)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mentorsInList.clear()
                if (snapshot!!.exists()) {
                    for (h in snapshot.children) {
                        if (h.hasChild("comps/".plus(category.toLowerCase()))) {
                            val mentor = h.getValue(Mentor::class.java)
                            mentorsInList.add(mentor!!)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    listView.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), R.string.firebaseListError, Toast.LENGTH_LONG).show()
            }
        })
    }
}
