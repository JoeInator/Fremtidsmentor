package com.nissen.johannes.fremtidsmentor.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.main.list_mentor_tem.view.*

class MentorListAdapter (context: Context, private val mentorsInList: ArrayList<Mentor>): BaseAdapter() {
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