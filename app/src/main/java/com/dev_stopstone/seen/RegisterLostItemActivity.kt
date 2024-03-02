package com.dev_stopstone.seen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev_stopstone.seen.databinding.ActivityRegisterLostItemBinding

class RegisterLostItemActivity: AppCompatActivity() {
    private val binding by lazy { ActivityRegisterLostItemBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}