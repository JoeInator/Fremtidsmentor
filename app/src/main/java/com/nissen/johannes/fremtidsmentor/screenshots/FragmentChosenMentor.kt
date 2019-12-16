package com.nissen.johannes.fremtidsmentor.screenshots

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import com.nissen.johannes.fremtidsmentor.entities.Schedule
import kotlinx.android.synthetic.main.fragment_chosen_mentor.view.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FragmentChosenMentor: Fragment() {

    private val compList1: ArrayList<String> = arrayListOf<String>()
    private val compList2: ArrayList<String> = arrayListOf<String>()
    lateinit var builder: StringBuilder
    lateinit var mPrefs: SharedPreferences
    lateinit var prefsEditor: SharedPreferences.Editor
    lateinit var ref: DatabaseReference
    lateinit var mentor: Mentor
    lateinit var date: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_chosen_mentor, container, false)

        mPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        prefsEditor = mPrefs.edit()
        ref = FirebaseDatabase.getInstance().getReference("users/mentor")
        val loading = ProgressDialog(requireContext())
        loading.setMessage("\t".plus(resources.getString(R.string.load_info)))
        loading.setCancelable(false)
        loading.show()

        getMentorFromFirebase(arguments!!.getString("name"))
        view.setBackgroundColor(resources.getColor(R.color.semiTransGrey)) //Some darker color
        view.subsribe_btn.visibility = View.GONE
        view.bookingBtn.visibility = View.GONE
        view.calendar_view.visibility = View.GONE

        Handler().postDelayed({
            view.setBackgroundColor(resources.getColor(android.R.color.transparent))
            loading.dismiss()
            loadPage(view)
            view.description_header.text = "\n".plus(mentor.getUsername()).plus(" ").plus(resources.getString(R.string.mentor_description))
            view.mentor_competencies.text = mentor.getUsername().plus(" ").plus(resources.getString(R.string.key_competencies))
        }, 500)

        return view
    }

    private fun FillLists(comps: HashMap<String, ArrayList<String>>) {

        val compsList = ArrayList<String>()

        for (h in comps) {
            compsList.addAll(h.value)
        }

        for (i in 0 until compsList.size/2) {
            compList1.add(compsList[i])
        }
        for (i in compsList.size/2 until compsList.size) {
            compList2.add(compsList[i])
        }
    }

    private fun loadPage(view: View) {

        //Set round picture of mentor
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cv_foto)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedBitmapDrawable.isCircular = true
        view.mentors_pic.setImageDrawable(roundedBitmapDrawable)

        val comps: HashMap<String, ArrayList<String>> = mentor.getComps()!!
        builder = StringBuilder()
        builder.append("")

        FillLists(comps)

        for (i in 0 until compList1.size) {
            builder.append(compList1[i]+"\n\n")
        }
        view.InfoView.vertical_scroll.horisontal_scroll.comp_list1.text = builder.toString()

        builder.clear()
        for (i in 0 until compList2.size) {
            builder.append(compList2[i]+"\n\n")
        }
        view.InfoView.vertical_scroll.horisontal_scroll.comp_list2.text = builder.toString()

        view.InfoView.vertical_scroll.mentor_description.text = mentor.getDescription()
        view.mentor_basicinfo.text = mentor.getTeaser()
        view.subsribe_btn.visibility = View.VISIBLE
        view.bookingBtn.visibility = View.VISIBLE

        view.bookingBtn.setOnClickListener{
            loadCalendar(view)
        }
    }

    /* Retrive the mentor from firebase */
    private fun getMentorFromFirebase(mentorName: String) {

        ref.addListenerForSingleValueEvent(object: ValueEventListener  {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), R.string.firebaseError, Toast.LENGTH_SHORT).show()
                activity!!.supportFragmentManager.popBackStack()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (m in p0.children) {
                    if (m.child("username").getValue(String::class.java).equals(mentorName)) {
                        mentor = m.getValue(Mentor::class.java)!!
                    }
                }
            }
        })
    }

    private fun viewCalendar(view: View) {
        view.subsribe_btn.visibility = View.GONE
        view.bookingBtn.visibility = View.GONE
        view.infoView.visibility = View.GONE
        view.mentors_pic.visibility = View.GONE
        view.mentor_basicinfo.visibility = View.GONE
        view.calendar_view.visibility = View.VISIBLE
        view.calendar_view.setBackgroundColor(resources.getColor(R.color.colorWhite))
        view.setBackgroundColor(resources.getColor(R.color.semiTransGrey))
        view.calendar_view.setBackgroundColor(resources.getColor(R.color.colorWhite))
    }

    private fun dismissCalender(view: View) {
        view.subsribe_btn.visibility = View.VISIBLE
        view.bookingBtn.visibility = View.VISIBLE
        view.infoView.visibility = View.VISIBLE
        view.mentors_pic.visibility = View.VISIBLE
        view.mentor_basicinfo.visibility = View.VISIBLE
        view.calendar_view.visibility = View.GONE
        view.setBackgroundColor(resources.getColor(android.R.color.transparent))
    }

    private fun bookDay(schedule: Schedule) {
        val tempRef = FirebaseDatabase.getInstance().getReference("bookings")
        schedule.ScheduleID = tempRef.push().getKey()
        schedule.ScheduleMentor = mentor.getUsername()
        schedule.ScheduleMentorID = mentor.getId()
        schedule.ScheduleMentee = mPrefs.getString("name","")
        schedule.ScheduleMenteeID = mPrefs.getString("userID", "")
        schedule.ScheduleDate = date

        tempRef.child(schedule.ScheduleID!!).setValue(schedule)
            .addOnCompleteListener {
                Log.d("BOOK", "Mentor is now booked")
            }

//        val tempRef2 = FirebaseDatabase.getInstance().getReference("users/mentor/".plus(mentor.getId()))
//        tempRef2.child("Schedules/".plus(schedule.ScheduleID)).setValue(schedule)

    }

    private fun loadCalendar(view: View) {
        viewCalendar(view)
        view.calendar.minDate = Calendar.getInstance().timeInMillis + (24 * 60 * 60 * 1000 * 3)
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        date = currentDate
        view.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            date = dayOfMonth.toString().plus("/").plus(month + 1).plus("/").plus(year)
        }

        view.setOnClickListener {
            dismissCalender(view)
        }
        view.book_btn.setOnClickListener {
            bookDay(Schedule())
            dismissCalender(view)
        }
        view.back_btn.setOnClickListener {
            dismissCalender(view)
        }
    }

}