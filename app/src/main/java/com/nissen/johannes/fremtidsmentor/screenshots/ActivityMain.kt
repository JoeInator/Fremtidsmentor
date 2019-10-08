package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.nissen.johannes.fremtidsmentor.R


class ActivityMain : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = FragmentLogin()
        supportFragmentManager.beginTransaction()
            .add(R.id.login_fragment, fragment)
            .commit()

    }
}
