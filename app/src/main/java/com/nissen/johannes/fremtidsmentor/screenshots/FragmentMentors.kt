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
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.main.fragment_chosen_mentor.view.*
import kotlinx.android.synthetic.main.list_mentor_tem.view.*

class FragmentMentors : Fragment() {

    private val mentorsInList = arrayListOf<Mentor>(
//            Mentor("Joe Biden", "nissen.johannes@gmail.com", "balle159", "Denne bruger har en gennemgående erfaring med Java",
//                "Some deeper description of the mentors backgroud", arrayListOf<String>("JAVA", "C#", "MySQL", "Domæne analyse", "kldd", "kmlml", "ikjlkoojoj", "jijljlj")),
//            Mentor("Poul Nissen", "poul@gmail.com","PoulRavnNissen", "Poul er teknisk chef i nordea-Danmark,\n" +
//                    "og har derfor meget erfaring med projektsyring","Some deeper description of the mentors backgroud", arrayListOf<String>(
//                "Projekt styring", "Python", "DNS", "Datakommunikation")),
//            Mentor("Jakob Melbye","jakobRocks@gmail.com","Netværkssikkerhed_Styrer","Jakob har en gennemgående viden\n" +
//                    "omkring netværkssikkerhed","Some deeper description of the mentors backgroud", arrayListOf<String>(
//                "Netværkssikkerhed", "Process og innovation", "Programmering i C", "Front-end developement"
//            ))
    )

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
        var adapter = Adapter(this.requireContext(), mentorsInList)

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


    private class Adapter(context: Context, private val mentorsInList: ArrayList<Mentor>): BaseAdapter() {
        private val mContext: Context

        init { mContext = context }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val main = if (convertView==null) { layoutInflater.inflate(R.layout.list_mentor_tem, parent,false) } else { convertView }

            val mentor = getItem(position) as Mentor

            val bitmap = BitmapFactory.decodeResource(main.resources, R.drawable.cv_foto)
            val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(main.resources, bitmap)
            roundedBitmapDrawable.isCircular = true
            main.mentor_img.setImageDrawable(roundedBitmapDrawable)

//            main.mentor_img.setImageResource(R.drawable.cv_foto)
            main.mentor_name.text = mentor.getUsername()
            main.mentor_descr.text = mentor.getTeaser()
            return main
        }

        override fun getCount(): Int {
            return mentorsInList.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return mentorsInList[position]
        }
    }


}
