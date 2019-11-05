package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_mentor_filter.view.*

class FragmentFilter : Fragment() {

    var cats = arrayListOf<String>("online marketing", "online branding", "chatbots", "UX and UI", "social media", "gamification")
    var img = arrayListOf<Int>()
    var filter = Bundle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mentor_filter, container, false)

        view.filter_view.setLayoutManager(StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        view.filter_view.adapter = ListeelemAdapter()


        return view
    }

    internal inner class ListeelemAdapter : RecyclerView.Adapter<ListeelemViewholder>() {
        override fun getItemCount(): Int {
            return cats.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeelemViewholder {
            val listeelementViews =
                layoutInflater.inflate(R.layout.category_item, parent, false)
            return ListeelemViewholder(listeelementViews)
        }

        override fun onBindViewHolder(vh: ListeelemViewholder, position: Int) {
            vh.Area.setText(cats.get(position))
            vh.logo.setImageResource(android.R.drawable.btn_star)
//            vh.logo.setImageResource(img.get(position))
        }
    }

    internal inner class ListeelemViewholder: RecyclerView.ViewHolder, View.OnClickListener {

        var Area: TextView
        var logo: ImageView

        constructor(listeelementViews: View) : super(listeelementViews) {
            Area = listeelementViews.findViewById(R.id.filter_text)
            logo = listeelementViews.findViewById(R.id.filter_logo)
            // Gør listeelementer klikbare og vis det ved at deres baggrunsfarve ændrer sig ved berøring
            Area.setBackgroundResource(android.R.drawable.list_selector_background)
            logo.setBackgroundResource(android.R.drawable.list_selector_background)
            Area.setOnClickListener(this)
            logo.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = super.getAdapterPosition()
            val Cat: String = cats[position]
            filter.putString("Filter", Cat)

            val nextFrag = FragmentMentors()
            nextFrag.setArguments(filter)
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.community_fragment, nextFrag)
                .addToBackStack(null)
                .commit()

        }
    }

}