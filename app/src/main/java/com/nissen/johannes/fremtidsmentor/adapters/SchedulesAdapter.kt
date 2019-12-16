package com.nissen.johannes.fremtidsmentor.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Schedule

class SchedulesAdapter(context: Context, private val Schedules: ArrayList<Schedule>) : RecyclerView.Adapter<SchedulesViewholder>() {

    val mContext: Context

    init {
        mContext = context
    }

    override fun getItemCount(): Int {
        return Schedules.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulesViewholder {
        val layoutInflater = LayoutInflater.from(mContext)
        val listeelementViews =
            layoutInflater.inflate(R.layout.list_schedules_item, parent, false)
        return SchedulesViewholder(listeelementViews, mContext)
    }

    override fun onBindViewHolder(vh: SchedulesViewholder, position: Int) {
        vh.sched.text = "   ".plus(Schedules.get(position).ScheduleDate)
        vh.info.text = /*"Date: ".plus(Schedules.get(position).ScheduleDate).plus("\n")*/
            "MENTOR: ".plus(Schedules.get(position).ScheduleMentor).plus("\n")
                .plus("MENTEE: ").plus(Schedules.get(position).ScheduleMentee)
    }
}

class SchedulesViewholder: RecyclerView.ViewHolder, View.OnClickListener {

    var sched: Button
    var info: TextView

    constructor(listeelementViews: View, context: Context) : super(listeelementViews) {
        sched = listeelementViews.findViewById(R.id.date_btn)
        info = listeelementViews.findViewById(R.id.schedule_info)

        // Sætter listeelementernes indhold og synlighed baggrunsfarve ændrer sig ved berøring
        sched.background = context.resources.getDrawable(R.drawable.layout_schedule_btn)
        info.background = context.resources.getDrawable(R.drawable.layout_schedule_btn)
        info.visibility = View.GONE

        // Gør listeelementer klikbare
        info.setOnClickListener(this)
        sched.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (info.visibility) {
            View.GONE -> { info.visibility = View.VISIBLE }
            View.VISIBLE -> { info.visibility = View.GONE }
        }
    }
}