package com.dev_stopstone.seen

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.dev_stopstone.seen.databinding.ActivityProfileAddBinding

class ProfileAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileAddBinding
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                binding.ivAddProfileImage.setImageURI(selectedImageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etInputProfileNickname.addTextChangedListener {
            binding.btnCompleteSetting.isEnabled = binding.etInputProfileNickname.text?.length!! >= 4
        }

        binding.ivAddProfileImage.setOnClickListener {
            openGallery()
        }

        binding.btnCompleteSetting.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(galleryIntent)
    }

    companion object {
        private const val REQUEST_PERMISSION_CODE = 100
    }
}