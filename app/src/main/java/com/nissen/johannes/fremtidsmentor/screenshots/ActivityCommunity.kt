package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.nissen.johannes.fremtidsmentor.R
import com.nissen.johannes.fremtidsmentor.controllers.ControllerRegistry
import com.nissen.johannes.fremtidsmentor.controllers.Interfaces.*
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_community.view.*

class ActivityCommunity : FragmentActivity() {

    private lateinit var mPrefs: SharedPreferences
    lateinit var FirebaseController: IFirebase
    private var onHome = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)
        Log.d("DIE!!", "Does the commuityAct start??")
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val cl = findViewById<ConstraintLayout>(R.id.community_layout)
        val path = mPrefs.getString("userType","")
        FirebaseController = ControllerRegistry.databaseController.DatabaseController
        FirebaseController.getUserFromFirebase(mPrefs.getString("userID",""))

        if (path == "user/mentor") {
            cl.community_fragment.setBackgroundResource(R.drawable.mentor)
        } else {
            cl.community_fragment.setBackgroundResource(R.drawable.mentee)
        }

        if (savedInstanceState == null) {
            val fragment = FragmentCommunity()
            supportFragmentManager.beginTransaction()
                .add(R.id.community_fragment, fragment)
                .commit()
        }


        //https://android-arsenal.com/details/1/7544 -- Implement this bottom navigationView if there is time
        bottomNavigation.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{
            when (it.itemId) {
                R.id.home -> {
                    val selectedFragment = FragmentCommunity()
                    if (onHome == false) {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_left, R.animator.exit_in_right)
                        .replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    }
                    onHome = true
                    return@OnNavigationItemSelectedListener true
                }
                  R.id.discover -> {
                      val selectedFragment = FragmentDiscover()
                      if (onHome == false) {
                          supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                      }
                      supportFragmentManager.beginTransaction()
                          .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                          .replace(R.id.community_fragment, selectedFragment)
                          .addToBackStack(null).commit()
                      onHome = false
                      return@OnNavigationItemSelectedListener true
                      }
                R.id.Mentor -> {
                    val selectedFragment = FragmentFilter()
                    if (onHome == false) {
                        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    }
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                        .replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    onHome = false
                    return@OnNavigationItemSelectedListener true
                }
                R.id.community -> {
                    val selectedFragment = FragmentCommunityForum()
                    if (onHome == false) {
                        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    }
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                        .replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    onHome = false
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    val selectedFragment = FragmentProfile()
                    if (onHome == false) {
                        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        }
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_in_left, R.animator.enter_from_left, R.animator.exit_in_right)
                        .replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    onHome = false
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    }

}
