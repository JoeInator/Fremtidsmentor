package com.nissen.johannes.fremtidsmentor.screenshots

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.nissen.johannes.fremtidsmentor.BuildConfig
import com.nissen.johannes.fremtidsmentor.R
import io.fabric.sdk.android.Fabric


class ActivityMain : FragmentActivity() {

    private val EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!BuildConfig.DEBUG) {
            Crashlytics.Builder()
                .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build()
                .also { crashlyticsKit ->
                    Fabric.with(this, crashlyticsKit)
                }
        }

        val fragment = FragmentChooseRole()
        supportFragmentManager.beginTransaction()
            .add(R.id.login_fragment, fragment)
            .commit()

    }
}
