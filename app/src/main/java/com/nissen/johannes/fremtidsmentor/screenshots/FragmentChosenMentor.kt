package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_chosen_mentor.*
import kotlinx.android.synthetic.main.fragment_chosen_mentor.view.*
import java.lang.StringBuilder

class FragmentChosenMentor: Fragment() {

    private val compList1: ArrayList<String> = arrayListOf<String>()
    private val compList2: ArrayList<String> = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_chosen_mentor, container, false)

        view.mentors_pic.setImageResource(R.drawable.cv_foto)
        val comps: ArrayList<String> = arguments!!.getStringArrayList("competencies")
        val builder: StringBuilder = StringBuilder()
        builder.append("")

        FillLists(comps)

        for (i in 0 until compList1.size) {
            builder.append(compList1[i]+"\n")
        }
        view.InfoView.vertical_scroll.horisontal_scroll.comp_list1.text = builder.toString()

        for (i in 0 until compList2.size) {
            builder.append(compList2[i]+"\n")
        }
        view.InfoView.vertical_scroll.horisontal_scroll.comp_list2.text = builder.toString()

        view.InfoView.vertical_scroll.mentor_description.text = arguments!!.getString("description")
        view.mentor_basicinfo.text = arguments!!.getString("name")

        view.bookingBtn.setOnClickListener{
            Toast.makeText(this.requireContext(), R.string.Not_Implemented, Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun FillLists(comps: ArrayList<String>) {
        for (i in 0 until comps.size/2) {
            //if (i <= comps.size / 2) {
                compList1.add(comps[i])
            //}
//            else if (i > comps.size / 2){
//                compList2.add(comps[i])
//            }
        }
        for (i in comps.size/2+1 until comps.size) {
            compList2.add(comps[i])
        }
    }
}