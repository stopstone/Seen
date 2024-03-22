package com.dev_stopstone.seenapp.ui.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeFragment : Fragment(), ItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private val items = mutableListOf<LostItem>()
    private val adapter = LostItemAdapter(items, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child("postImages")
        database = Firebase.database
        val postRef = database.getReference("post")

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lifecycleScope.launch {
                    items.clear()
                    for (data in dataSnapshot.children) {
                        val item = data.getValue(LostItem::class.java)
                        item!!.imageUrls.addAll(getFileUris(listRef))
                        items.add(item)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHomeItemList.adapter = adapter

        binding.btnAddLostItemButton.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeToRegisterLostItem()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickLostItem(lostItem: LostItem) {
        val action =
            HomeFragmentDirections.actionHomeToLostDetail(lostItem)
        findNavController().navigate(action)
    }
}

suspend fun getFileUris(listRef: StorageReference): List<Uri> = withContext(Dispatchers.IO) {
    val listResult = listRef.listAll().await()
    val urlList = mutableListOf<Uri>()

    for (item in listResult.items) {
        val uri = item.downloadUrl.await()
        urlList.add(uri)
    }

    urlList
}
