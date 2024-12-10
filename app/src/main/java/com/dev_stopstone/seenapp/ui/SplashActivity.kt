package com.dev_stopstone.seenapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dev_stopstone.seenapp.MainActivity
import com.dev_stopstone.seenapp.R
import com.dev_stopstone.seenapp.ui.profile.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        intent = when(auth.currentUser) {
            null -> Intent(this, LoginActivity::class.java)
            else -> Intent(this, MainActivity::class.java)
        }

        lifecycleScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                startActivity(intent)
                finish()
            }
        }
    }
}