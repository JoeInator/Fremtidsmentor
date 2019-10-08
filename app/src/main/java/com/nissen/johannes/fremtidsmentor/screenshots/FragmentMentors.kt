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
import com.nissen.johannes.fremtidsmentor.adapters.MentorList_Adapter
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_mentor.*

class FragmentMentors : Fragment() {

    private val mentorsInList = arrayListOf<Mentor>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mentor, container, false)

        mentorsInList.add(Mentor("1", "Joe Biden", "balle159", "Denne bruger har en gennemgående erfaring med Java"))
        mentorsInList.add(Mentor("2", "Poul Nissen","PoulRavnNissen", "Poul er Teknisk i nordea-Danmark,\n" +
                "og har derfor meget erfaring med projektsyring"))
        mentorsInList.add(Mentor("3","Jakob Melbye","Netværkssikkerhed_Styrer","Jakob har en gennemgående viden\n" +
                "omkring netværkssikkerhed"))

        var adapter = Adapter(this.requireActivity())
        mentor_list.adapter = adapter

//        adapter.notifyDataSetChanged()

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



    private class Adapter(context: Context): BaseAdapter() {

        private val mContext: Context

        init {
            mContext = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val layoutInflater = LayoutInflater.from(mContext)
//            val main = layoutInflater.inflate(R.layout.mentor_list_item, parent, false)
//            return main
            val textview = TextView(mContext)
            textview.text = "Why the fuck is this not working!!??"
            return textview
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "FragmentMentors.Adapter.getItemID shouldn't be called"
        }

    }


}