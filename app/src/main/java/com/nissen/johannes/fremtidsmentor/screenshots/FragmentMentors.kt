package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.main.fragment_mentor.view.*
import kotlinx.android.synthetic.main.mentor_list_item.view.*

class FragmentMentors : Fragment() {

    private val mentorsInList = arrayListOf<Mentor>(
            Mentor("1", "Joe Biden", "balle159", "Denne bruger har en gennemgående erfaring med Java"),
            Mentor("2", "Poul Nissen","PoulRavnNissen", "Poul er teknisk chef i nordea-Danmark,\n" +
                    "og har derfor meget erfaring med projektsyring"),
            Mentor("3","Jakob Melbye","Netværkssikkerhed_Styrer","Jakob har en gennemgående viden\n" +
                    "omkring netværkssikkerhed")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mentor, container, false)

        view.mentor_list.setOnItemClickListener { parent, view, position, id ->  }

        val adapter = Adapter(this.requireContext(), mentorsInList)
        view.mentor_list.adapter = adapter

//        adapter.notifyDataSetChanged()
//        view.mentor_list.setAdapter(adapter);
        return view
    }


    class Adapter(context: Context, private val mentorsInList: ArrayList<Mentor>): BaseAdapter() {

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
            main.mentor_descr.text = mentor.getDescription()

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