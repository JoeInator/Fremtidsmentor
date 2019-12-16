package com.nissen.johannes.fremtidsmentor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.screenshots.FragmentInterestsAdd

class AllInterestsAdapter (context: Context, private val Interests: ArrayList<String>, private val checkedInterests: ArrayList<String>): RecyclerView.Adapter<AllInterestsViewholder>() {

    val mContext: Context

    init {
        mContext = context
    }

    override fun getItemCount(): Int {
        return Interests.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllInterestsViewholder {
        val layoutInflater = LayoutInflater.from(mContext)
        val listeelementViews =
            layoutInflater.inflate(R.layout.list_all_interests_possible_item, parent, false)
        return AllInterestsViewholder(listeelementViews, mContext)
    }

    override fun onBindViewHolder(vh: AllInterestsViewholder, position: Int) {
        val item = Interests[position]
        vh.checkBox.text = item

        //in some cases, it will prevent unwanted situations
        vh.checkBox.setOnCheckedChangeListener(null)

        //Setting the initial checked state of the checkboxes
        vh.checkBox.isChecked = checkedInterests.contains(item)

        //used for setting the checked state of a given checkbox
        vh.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                //setting a given checkbox's status
                vh.checkBox.setSelected(isChecked)
                if (isChecked) {
                    checkedInterests.add(vh.checkBox.text.toString())
                } else {
                    checkedInterests.remove(vh.checkBox.text.toString())
                }
            }
        })
    }
}

class AllInterestsViewholder: RecyclerView.ViewHolder {
    var checkBox: CheckBox

    constructor(listeelementViews: View, context: Context) : super(listeelementViews) {
        checkBox = listeelementViews.findViewById(R.id.checkBox)
    }

}