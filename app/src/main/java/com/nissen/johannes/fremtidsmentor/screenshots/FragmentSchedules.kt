package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nissen.johannes.fremtidsmentor.R

class FragmentSchedules: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_schedules, container, false)

        if (1+1==1) {
            loadSchedules()
        }


        return view
    }

    private fun loadSchedules() {

    }

}