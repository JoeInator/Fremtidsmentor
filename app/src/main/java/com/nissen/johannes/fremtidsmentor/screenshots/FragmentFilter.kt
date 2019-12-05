package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.fragment_mentor_filter.view.*

class FragmentFilter : Fragment() {

    var cats = arrayListOf<String>("online marketing", "online branding", "chatbots", "UX and UI", "social media", "gamification")
    var img = arrayListOf<Int>(R.drawable.icon_online_marketing, R.drawable.icon_online_branding,
        R.drawable.icon_chatbot, R.drawable.icon_ux, R.drawable.icon_social_media, R.drawable.icon_gamification,
        R.drawable.icon_science, R.drawable.icon_social, R.drawable.icon_humanities, R.drawable.icon_software, R.drawable.icon_chemistry,
        R.drawable.icon_database, R.drawable.icon_management, R.drawable.icon_management)
    var filter = Bundle()
    lateinit var ref: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mentor_filter, container, false)

        ref = FirebaseDatabase.getInstance().getReference("categories")

        class AsyncTask1(): AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String {
                getCategoryList()
                return "ghj"
            }

            override fun onPreExecute() {
                super.onPreExecute()
                view.filter_view.visibility = View.GONE
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                view.filter_view.setLayoutManager(StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                view.filter_view.adapter = ListeelemAdapter()
                view.filter_view.visibility = View.VISIBLE
            }

        }

        AsyncTask1().execute()

        return view
    }

    private fun getCategoryList() {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cats.clear()
//                cats = snapshot.getValue(ArrayList<String>::class.java)!!
                for (h in snapshot.children) {
                    cats.add(h.value.toString())
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), R.string.firebaseListError, Toast.LENGTH_LONG).show()
            }
        })
    }

    internal inner class ListeelemAdapter : RecyclerView.Adapter<ListeelemViewholder>() {
        override fun getItemCount(): Int {
            return cats.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeelemViewholder {
            val listeelementViews =
                layoutInflater.inflate(R.layout.list_filter_item, parent, false)
            return ListeelemViewholder(listeelementViews)
        }

        override fun onBindViewHolder(vh: ListeelemViewholder, position: Int) {
            vh.Area.setText(cats.get(position))
            val layoutParams = vh.logo.layoutParams
            vh.logo.setBackgroundResource(img[position])
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