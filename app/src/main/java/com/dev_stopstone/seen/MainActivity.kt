package com.dev_stopstone.seen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dev_stopstone.seen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val user = Intent().getParcelableExtra("user", User::class.java)

        binding.ivUserProfile.setImageURI(user?.profileImageUrl)
        binding.tvUserNickname.text = user?.nickName ?: ""
    }
}