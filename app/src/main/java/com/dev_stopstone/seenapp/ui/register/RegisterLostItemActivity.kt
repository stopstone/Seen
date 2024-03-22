package com.dev_stopstone.seenapp.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.dev_stopstone.seenapp.data.Location
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.ActivityRegisterLostItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegisterLostItemActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterLostItemBinding.inflate(layoutInflater) }
    private lateinit var postGalleryAdapter: PostGalleryAdapter
    private var imageUri: ArrayList<Uri> = ArrayList()
    private lateinit var location: Location
    private val database = Firebase.database
    private lateinit var postRef: DatabaseReference

    private var isText = false
    private var isLocation = false
    private var isDate = false
    private var isReward = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        postRef = database.reference.child("post").push()
        postGalleryAdapter = PostGalleryAdapter(imageUri)

        with(binding) {
            etRegisterItemTitle.addTextChangedListener {
                val itemTitle = it ?: ""
                isText = isValidText(itemTitle)
                updateButtonEnableState()
            }
            etRegisterItemLocation.addTextChangedListener {
                val itemLocation = it ?: ""
                isLocation = isValidText(itemLocation)
                updateButtonEnableState()
            }
            etRegisterItemDate.addTextChangedListener {
                val itemDate = it ?: ""
                isDate = isValidText(itemDate)
                updateButtonEnableState()
            }
            etRegisterItemRewardPrice.addTextChangedListener {
                val itemReward = it ?: ""
                isReward = isValidText(itemReward)
                updateButtonEnableState()
            }
        }

        with(binding) {
            setListenerEnable(etRegisterItemLocation)
            setListenerEnable(etRegisterItemDate)

            ivRegisterItemAddImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                activityResult.launch(intent)
            }
            rvRegisterPhotoList.adapter = postGalleryAdapter

            etRegisterItemLocation.setOnClickListener {
                val intent = Intent(applicationContext, RegisterLocationActivity::class.java)
                intent.putExtra("key", postRef.key.toString())
                resultLauncher.launch(intent)
            }

            etRegisterItemDate.setOnClickListener {
                showDatePickerDialog()
            }
            btnRegisterCompleteButton.setOnClickListener {
                val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
                val lostItem = LostItem(
                    postId = currentUser,
                    title = "${etRegisterItemTitle.text}",
                    description = "${etRegisterItemDescription.text}",
                    location = location,
                    lostDate = etRegisterItemDate.text.toString(),
                    createAt = getCurrentDate(),
                    rewardPrice = "${etRegisterItemRewardPrice.text}"
                )

                postRef.setValue(lostItem).addOnSuccessListener {
                    for (count in 0 until imageUri.size) {
                        imageUpload(count)
                    }
                }
            }
        }
    }

    private fun imageUpload(count: Int) {
        val storage = Firebase.storage
        val storageRef = storage.getReference("${postRef.key}")
        val fileName = "${postRef.key}_${count}"

        val mountainsRef = storageRef.child("${fileName}.png")
        val uploadTask = mountainsRef.putFile(imageUri[count])

        uploadTask.addOnSuccessListener {
            finish()
        }
    }

    private fun isValidText(text: CharSequence) = text.isNotEmpty()

    private fun updateButtonEnableState() {
        binding.btnRegisterCompleteButton.isEnabled =
            isText && isLocation && isDate && isReward
    }

    private fun setListenerEnable(editText: EditText) {
        editText.keyListener = null
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val resultLocation = data?.getParcelableExtra("result", Location::class.java)

                if (resultLocation != null) {
                    location = resultLocation
                }

                binding.etRegisterItemLocation.setText(resultLocation!!.title)
            }
        }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            if (it.data!!.clipData != null) {
                val count = it.data!!.clipData!!.itemCount
                for (index in 0 until count) {
                    val imageUri = it.data!!.clipData!!.getItemAt(index).uri
                    this.imageUri.add(imageUri)
                }
            } else { //싱글 이미지
                val imageUri = it.data!!.data
                this.imageUri.add(imageUri!!)
            }
            postGalleryAdapter.notifyDataSetChanged()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = "${year}년 ${month + 1}월 ${day}일"
                binding.etRegisterItemDate.setText(selectedDate)
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePickerDialog.show()
    }
}