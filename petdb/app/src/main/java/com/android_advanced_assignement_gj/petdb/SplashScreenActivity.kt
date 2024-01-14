package com.android_advanced_assignement_gj.petdb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android_advanced_assignement_gj.petdb.databinding.SplashScreenBinding
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            delay(1000) // Wait for 1 second
            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                // Override the default transition animations
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
                finish()
            }
        }
    }
}
