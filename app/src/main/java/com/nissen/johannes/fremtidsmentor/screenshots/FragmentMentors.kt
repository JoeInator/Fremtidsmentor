package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_mentor.*
import kotlinx.android.synthetic.main.fragment_mentor.view.*
import kotlinx.android.synthetic.main.mentor_list_item.view.*

class FragmentMentors : Fragment() {

    private val mentorsInList = arrayListOf<Mentor>(
            Mentor("1", "Joe Biden", "balle159", "Denne bruger har en gennemgående erfaring med Java",
                "Some deeper description of the mentors backgroud", arrayListOf<String>("JAVA", "C#", "MySQL", "Domæne analyse", "kldd", "kmlml", "ikjlkoojoj", "jijljlj")),
            Mentor("2", "Poul Nissen","PoulRavnNissen", "Poul er teknisk chef i nordea-Danmark,\n" +
                    "og har derfor meget erfaring med projektsyring","Some deeper description of the mentors backgroud", arrayListOf<String>(
                "Projekt styring", "Python", "DNS", "Datakommunikation")),
            Mentor("3","Jakob Melbye","Netværkssikkerhed_Styrer","Jakob har en gennemgående viden\n" +
                    "omkring netværkssikkerhed","Some deeper description of the mentors backgroud", arrayListOf<String>(
                "Netværkssikkerhed", "Process og innovation", "Programmering i C", "Front-end developement"
            ))
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mentor, container, false)

                view.mentor_list.setOnItemClickListener { parent, view, position, id ->
            val selectedMentor = FragmentChosenMentor()

            val pdf_args = Bundle()

            pdf_args.putString("name", mentorsInList.get(position).getUsername())
            pdf_args.putString("description", mentorsInList.get(position).getDescription())
            pdf_args.putStringArrayList("competencies", mentorsInList.get(position).getComps())
            selectedMentor.setArguments(pdf_args)

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.community_fragment, selectedMentor)
                .addToBackStack(null)
                .commit()
        }

        var adapter = Adapter(this.requireActivity(), mentorsInList)
        view.mentor_list.adapter = adapter

        return view
    }


    private fun generateDummies(): ArrayList<Mentor> {
        var result = ArrayList<Mentor>()

        for (i in 0..2){
            var user: Mentor = mentorsInList[i]
            result.add(user)
        }

        return result
    }



    private class Adapter(context: Context, mentorsInList: ArrayList<Mentor>): BaseAdapter() {

        private val mContext: Context

        init {
            mContext = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val main = layoutInflater.inflate(R.layout.mentor_list_item, parent, false)
            
            val mentor = getItem(position) as Mentor
            main.mentor_img.setImageResource(R.drawable.cv_foto)
            main.mentor_name.text = mentor.getUsername()
            main.mentor_descr.text = mentor.getTeaser()
            
            return main
        }

        override fun getCount(): Int {
            return Mentors.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return mentorsInList[position]
        }

    }


}
