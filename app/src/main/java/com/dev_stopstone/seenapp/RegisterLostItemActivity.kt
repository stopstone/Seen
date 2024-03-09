package com.dev_stopstone.seenapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev_stopstone.seenapp.databinding.ActivityRegisterLostItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterLostItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterLostItemBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        with(binding) {
            btnRegisterCompleteButton.setOnClickListener {
                val lostItem = LostItem(
                    id = "0",
                    title = "${etRegisterItemTitle.text}",
                    itemUrlImage = "image",
                    description = "${etRegisterItemDescription.text}",
                    location = "갤러리아 백화점",
                    lostDate = "2024-04-02",
                    createAt = "2024-03-03",
                    rewardPrice = 50000
                )

                // 현재 로그인된 사용자 정보 가져오기
                val currentUser = FirebaseAuth.getInstance().currentUser

                val database = FirebaseDatabase.getInstance()
                database.reference.child("users").setValue(lostItem)
            }
        }
    }
}