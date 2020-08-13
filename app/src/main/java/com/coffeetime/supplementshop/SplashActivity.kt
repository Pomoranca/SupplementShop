package com.coffeetime.supplementshop

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : Activity() {

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            startAnimation()
        }

    }

    private suspend fun startAnimation() {
        motion_layout.transitionToEnd()
        delay(1100)

    }


}