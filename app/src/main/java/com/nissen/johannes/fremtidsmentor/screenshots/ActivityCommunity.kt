package com.nissen.johannes.fremtidsmentor.screenshots

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_community.view.*
import kotlinx.android.synthetic.main.fragment_community_user.*

class ActivityCommunity : FragmentActivity() {

    private lateinit var mPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val cl = findViewById<ConstraintLayout>(R.id.community_layout)
        val path = mPrefs.getString("userType","")

        if (path == "user/mentor") {
            cl.community_fragment.setBackgroundResource(R.drawable.mentor)
        } else {
            cl.community_fragment.setBackgroundResource(R.drawable.mentee)
        }

        val fragment = FragmentCommunity()
        supportFragmentManager.beginTransaction()
            .add(R.id.community_fragment, fragment)
            .commit()


        //https://android-arsenal.com/details/1/7544 -- Implement this bottom navigationView if there is time
        bottomNavigation.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{
            when (it.itemId) {
                R.id.home -> {
                    val selectedFragment = FragmentCommunity()
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    return@OnNavigationItemSelectedListener true
                }
                  R.id.discover -> {
                      val selectedFragment = FragmentDiscover()
                      supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                      supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                          .addToBackStack(null).commit()
                      return@OnNavigationItemSelectedListener true
                      }
                R.id.Mentor -> {
                    val selectedFragment = FragmentFilter()
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.community -> {
                    val selectedFragment = FragmentCommunityForum()
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    val selectedFragment = FragmentProfile()
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .addToBackStack(null).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    }

}
