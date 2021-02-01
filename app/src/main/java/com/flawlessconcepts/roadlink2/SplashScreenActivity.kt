package com.flawlessconcepts.roadlink2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val startAppProper = Intent(this@SplashScreenActivity, MainActivity::class.java)

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        startActivity(startAppProper)
                        finish()
                    }
                }
            },
            2600
        )

    }
}