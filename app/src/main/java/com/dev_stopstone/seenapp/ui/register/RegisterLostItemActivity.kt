package com.dev_stopstone.seenapp.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import com.dev_stopstone.seenapp.data.Location
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.ActivityRegisterLostItemBinding
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
    private var imageUri: MutableList<String> = mutableListOf()
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

        setValidListener()

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
                val lostItem = LostItem(
                    postId = "${postRef.key}",
                    title = "${etRegisterItemTitle.text}",
                    imageUris = getAllImageList("${postRef.key}"),
                    description = "${etRegisterItemDescription.text}",
                    location = location,
                    lostDate = etRegisterItemDate.text.toString(),
                    createAt = getCurrentDate(),
                    rewardPrice = "${etRegisterItemRewardPrice.text}"
                )
                postRef.setValue(lostItem).addOnCompleteListener {
                    finish()
                }
            }
        }
    }

    private fun getAllImageList(postId: String): MutableList<String> {
        val images = mutableListOf<String>()
        for (count in 0 until imageUri.size) {
            images.add(imageUpload(count, postId))
        }
        return images
    }

    private fun setValidListener() {
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
    }

    private fun imageUpload(count: Int, postId: String): String {
        val storage = Firebase.storage
        val storageRef = storage.getReference("postImages").child(postId)
        val fileName = "${postRef.key}_${count}"

        val imageRef = storageRef.child("${fileName}.png")
        imageRef.putFile(imageUri[count].toUri())
        return imageRef.toString()
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
                    val uri = it.data!!.clipData!!.getItemAt(index).uri
                    imageUri.add("$uri")
                }
            } else { //싱글 이미지
                val uri = it.data!!.data
                imageUri.add("$uri")
            }
            postGalleryAdapter.notifyDataSetChanged()
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd hh:mm:ss", Locale.KOREA)
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