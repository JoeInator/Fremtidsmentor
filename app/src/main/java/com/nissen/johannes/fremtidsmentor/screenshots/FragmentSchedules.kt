package com.nissen.johannes.fremtidsmentor.screenshots

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Schedule
import kotlinx.android.synthetic.main.fragment_schedules.view.*
import kotlin.collections.ArrayList

class FragmentSchedules: Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var mPrefs: SharedPreferences
    private lateinit var Schedules: ArrayList<Schedule>
    private lateinit var prefsEditor: SharedPreferences.Editor


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_schedules, container, false)
        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        ref = FirebaseDatabase.getInstance().getReference("bookings")
//        Toast.makeText(requireContext(),mPrefs.getString("userType","Der er en fejl"),Toast.LENGTH_SHORT).show()
        if (1+1==2) {
            view.conText.visibility = View.GONE
            view.schedulesList.visibility = View.VISIBLE
            loadSchedules(view)
            view.setBackgroundColor(resources.getColor(R.color.semiTransGrey)) //Some darker color
            val loading = ProgressDialog(requireContext())
            loading.setMessage("\t".plus(resources.getString(R.string.load_info)))
            loading.setCancelable(false)
            loading.show()

            Handler().postDelayed({
                view.setBackgroundColor(resources.getColor(android.R.color.transparent))
//                view.schedulesList.adapter = ListeelemAdapter()
                loading.dismiss()
            },500)


        }


        return view
    }

    private fun loadSchedules(view: View) {

        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                Schedules = ArrayList<Schedule>()
                for (h in p0.children) {
                    if (h.child("scheduleMentee").getValue(String::class.java).equals(mPrefs.getString("name", ""))) {
//                        for (i in h.child("Schedules").children) {
                            val schedule = h.getValue(Schedule::class.java)
                            Schedules.add(schedule!!)
//                        }
                    }
                }
                view.schedulesList.setLayoutManager(LinearLayoutManager(requireContext()))
                view.schedulesList.adapter = ListeelemAdapter()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }

    internal inner class ListeelemAdapter : RecyclerView.Adapter<ListeelemViewholder>() {
        override fun getItemCount(): Int {
            return Schedules.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeelemViewholder {
            val listeelementViews =
                layoutInflater.inflate(R.layout.list_schedules_item, parent, false)
            return ListeelemViewholder(listeelementViews)
        }

        override fun onBindViewHolder(vh: ListeelemViewholder, position: Int) {
            vh.sched.text = "   ".plus(Schedules.get(position).ScheduleDate)
            vh.info.text = /*"Date: ".plus(Schedules.get(position).ScheduleDate).plus("\n")*/
                "MENTOR: ".plus(Schedules.get(position).ScheduleMentor).plus("\n")
                .plus("MENTEE: ").plus(Schedules.get(position).ScheduleMentee)
        }
    }

    internal inner class ListeelemViewholder: RecyclerView.ViewHolder, View.OnClickListener {

        var sched: Button
        var info: TextView

        constructor(listeelementViews: View) : super(listeelementViews) {
            sched = listeelementViews.findViewById(R.id.date_btn)
            info = listeelementViews.findViewById(R.id.schedule_info)

            // Sætter listeelementernes indhold og synlighed baggrunsfarve ændrer sig ved berøring
            sched.background = resources.getDrawable(R.drawable.layout_schedule_btn)
            info.background = resources.getDrawable(R.drawable.layout_schedule_btn)
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

    //https://android-arsenal.com/details/1/7886 -- Use this library instead (same, but allows for dropdown)

}