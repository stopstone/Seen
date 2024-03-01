package com.dev_stopstone.seen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.dev_stopstone.seen.databinding.ActivityProfileAddBinding

class ProfileAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileAddBinding
    private lateinit var selectedImageUri: Uri
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data!!
                binding.ivAddProfileImage.setImageURI(selectedImageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListener()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(galleryIntent)
    }

    private fun createUserInfo(): User {
        return User(
            nickName = "${binding.etInputProfileNickname.text}",
            profileImageUrl = selectedImageUri
        )
    }

    private fun setListener() {
        with(binding) {
            etInputProfileNickname.addTextChangedListener {
                btnCompleteSetting.isEnabled =
                    etInputProfileNickname.text?.length!! >= 4
            }

            ivAddProfileImage.setOnClickListener {
                openGallery()
            }

            btnCompleteSetting.setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("user", createUserInfo())
                startActivity(intent)
                finish()
            }
        }
    }
}