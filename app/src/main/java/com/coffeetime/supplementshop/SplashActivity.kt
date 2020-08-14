package com.coffeetime.supplementshop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.coffeetime.supplementshop.network.User
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
            startActivity()
        }

    }

    private suspend fun startAnimation() {
        motion_layout.transitionToEnd()
        delay(1000)
    }

    private fun startActivity() {

        val intent = if (isUserLoggedIn()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)

        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun isUserLoggedIn(): Boolean {
        return User.getToken(this) != null

    }


}