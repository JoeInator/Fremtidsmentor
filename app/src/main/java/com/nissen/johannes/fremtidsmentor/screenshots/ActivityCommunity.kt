package com.nissen.johannes.fremtidsmentor.screenshots

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.nissen.johannes.fremtidsmentor.R
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.fragment_community_user.*

class ActivityCommunity : FragmentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val fragment = FragmentCommunity()
        supportFragmentManager.beginTransaction()
            .add(R.id.community_fragment, fragment)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{
            when (it.itemId) {
                R.id.home -> {
                    val selectedFragment = FragmentCommunity()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.community_fragment, selectedFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                  R.id.discover -> {
                      val selectedFragment = FragmentDiscover()
                      supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                          .commit()
                      return@OnNavigationItemSelectedListener true
                      }
                R.id.Mentor -> {
                    val selectedFragment = FragmentMentors()
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.community -> {
                    val selectedFragment = FragmentCommunityForum()
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    val selectedFragment = FragmentProfile()
                    supportFragmentManager.beginTransaction().replace(R.id.community_fragment, selectedFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    }

}
