package com.dev_stopstone.seenapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.dev_stopstone.seenapp.MainActivity
import com.dev_stopstone.seenapp.data.User
import com.dev_stopstone.seenapp.databinding.ActivityProfileAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class ProfileAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileAddBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectedImageUri = Uri.EMPTY
        auth = Firebase.auth
        setListener()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImage.launch(galleryIntent)
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data!!
                Glide.with(this)
                    .load(selectedImageUri)
                    .into(binding.ivAddProfileImage)
            }
        }

    private fun createUserInfo(): User {
        database = Firebase.database
        val uid = auth.currentUser?.uid.toString()
        val userRef = database.getReference("users").push().child(uid)

        val user = User(
            uId = uid,
            nickName = "${binding.etInputProfileNickname.text}",
        )
        userRef.child("profile").setValue(user)
        profileUpload(selectedImageUri, uid)

        return user
    }

    private fun profileUpload(uri: Uri, uid: String) {
        storage = Firebase.storage
        val profileRef = storage.getReference("users")
        val imageRef = profileRef.child("${uid}.png")
        imageRef.putFile(uri)
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
                createUserInfo()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}