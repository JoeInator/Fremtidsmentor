package com.nissen.johannes.fremtidsmentor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nissen.johannes.fremtidsmentor.R

class InterestsAdapter(context: Context, private val Interests: ArrayList<String>): RecyclerView.Adapter<InterestsViewholder>() {

    val mContext: Context

    init {
        mContext = context
    }

    override fun getItemCount(): Int {
        return Interests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewholder {
        val layoutInflater = LayoutInflater.from(mContext)
        val listeelementViews =
            layoutInflater.inflate(R.layout.list_interests_item, parent, false)
        return InterestsViewholder(listeelementViews, mContext)
    }

    override fun onBindViewHolder(vh: InterestsViewholder, position: Int) {
        vh.interestsTextView.text = Interests[position]
        vh.interestsTextView.textSize = 22F
        if (position == Interests.lastIndex){ vh.separetor.visibility = View.VISIBLE }
    }
}

class InterestsViewholder: RecyclerView.ViewHolder {

    var interestsTextView: TextView
    var separetor: ImageView

    constructor(listeelementViews: View, context: Context) : super(listeelementViews) {
        interestsTextView = listeelementViews.findViewById(R.id.interest_view)
        separetor = listeelementViews.findViewById(R.id.separetor)

    }

}