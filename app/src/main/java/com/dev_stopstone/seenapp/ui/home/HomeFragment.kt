package com.dev_stopstone.seenapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.FragmentHomeBinding
import com.dev_stopstone.seenapp.ui.register.LocationBottomSheetFragment.Companion.TAG
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


class HomeFragment : Fragment(), ItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private val items = mutableListOf<LostItem>()
    private val adapter = LostItemAdapter(items, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database
        storage = Firebase.storage
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
        binding.refreshLayout.setOnRefreshListener {
            loadPostData()
            binding.refreshLayout.isRefreshing = false
        }

        binding.btnAddLostItemButton.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeToRegisterLostItem()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        loadPostData()
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

    private fun loadPostData() {
        val postRef = database.getReference("post")
        postRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for (postValue in dataSnapshot.children) {
                    val item = postValue.getValue(LostItem::class.java)
                    items.add(item!!)
                }
                items.reverse()
                binding.rvHomeItemList.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}