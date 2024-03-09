package com.dev_stopstone.seenapp.ui.register

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.ActivityRegisterLostItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterLostItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterLostItemBinding.inflate(layoutInflater) }
    private lateinit var postGalleryAdapter: PostGalleryAdapter
    private var items: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        postGalleryAdapter = PostGalleryAdapter(items)
        with(binding) {
            ivRegisterItemAddImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                activityResult.launch(intent)
            }
            rvRegisterPhotoList.adapter = postGalleryAdapter

            btnRegisterCompleteButton.setOnClickListener {
                val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
                val database = FirebaseDatabase.getInstance()
                val postRef = database.reference.child("post").push()
                val lostItem = LostItem(
                    postId = currentUser,
                    title = "${etRegisterItemTitle.text}",
                    itemUrlImage = "image",
                    description = "${etRegisterItemDescription.text}",
                    location = "갤러리아 백화점",
                    lostDate = "2024-04-02",
                    createAt = "2024-03-03",
                    rewardPrice = 50000
                )
                postRef.setValue(lostItem).addOnSuccessListener {
                    finish()
                }
            }
        }
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            if (it.data!!.clipData != null) {
                val count = it.data!!.clipData!!.itemCount
                for (index in 0 until count) {
                    //이미지 담기
                    val imageUri = it.data!!.clipData!!.getItemAt(index).uri
                    //이미지 추가
                    items.add(imageUri)
                }
            } else { //싱글 이미지
                val imageUri = it.data!!.data
                items.add(imageUri!!)
            }
            postGalleryAdapter.notifyDataSetChanged()
        }
    }
}