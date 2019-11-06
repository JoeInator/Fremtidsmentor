package com.nissen.johannes.fremtidsmentor.screenshots

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.entities.Mentor
import kotlinx.android.synthetic.main.fragment_chosen_mentor.view.*
import java.lang.StringBuilder

class FragmentChosenMentor: Fragment() {

    private val compList1: ArrayList<String> = arrayListOf<String>()
    private val compList2: ArrayList<String> = arrayListOf<String>()
    lateinit var builder: StringBuilder
    lateinit var mPrefs: SharedPreferences
    lateinit var prefsEditor: SharedPreferences.Editor
    lateinit var ref: DatabaseReference
    lateinit var mentor: Mentor

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

        Handler().postDelayed({
            view.background = resources.getDrawable(R.drawable.frontpage, null)
            loading.dismiss()
            loadPage(view)
            view.mentor_competencies.text = resources.getString(R.string.key_competencies)
        }, 5000)

        return view
    }

    private fun FillLists(comps: ArrayList<String>) {
        for (i in 0 until comps.size/2) {
            compList1.add(comps[i])
        }
        for (i in comps.size/2+1 until comps.size) {
            compList2.add(comps[i])
        }
    }

    private fun loadPage(view: View) {
        view.mentors_pic.setImageResource(R.drawable.cv_foto)
        val comps: ArrayList<String> = mentor.getComps()!!
        builder = StringBuilder()
        builder.append("")

        FillLists(comps)

        for (i in 0 until compList1.size) {
            builder.append(compList1[i]+"\n")
        }
        view.InfoView.vertical_scroll.horisontal_scroll.comp_list1.text = builder.toString()

        builder.clear()
        for (i in 0 until compList2.size) {
            builder.append(compList2[i]+"\n")
        }
        view.InfoView.vertical_scroll.horisontal_scroll.comp_list2.text = builder.toString()

        view.InfoView.vertical_scroll.mentor_description.text = mentor.getDescription()
        view.mentor_basicinfo.text = mentor.getTeaser()
        view.subsribe_btn.visibility = View.VISIBLE
        view.bookingBtn.visibility = View.VISIBLE

        view.bookingBtn.setOnClickListener{
            Toast.makeText(this.requireContext(), R.string.calendarView_under_construction, Toast.LENGTH_SHORT).show()
        }
    }

    /* Retrive the mentor from firebase */
    private fun getMentorFromFirebase(mentorName: String) {

        ref.addValueEventListener(object: ValueEventListener  {
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

}