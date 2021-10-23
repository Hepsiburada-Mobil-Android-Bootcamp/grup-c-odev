package com.bootcampteamc.odev.ui.home.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentCategoryListBinding
import com.bootcampteamc.odev.infra.BaseFragment
import com.bootcampteamc.odev.ui.home.adapter.ProductAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class CategoryListFragment(private val category: String) : BaseFragment() {


    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    override val viewModel: CategoryListViewModel by viewModels {
        CategoryListViewModelFactory()
    }

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var adapter: ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        binding.apply {
            rvProducts.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
        }


        return binding.root
    }


    private fun setupRecyclerView() {
        val query: Query = db.collection("test").whereEqualTo("category", category)
        adapter = ProductAdapter(query, parentFragmentManager)
        adapter.itemClickListener = viewModel.itemClickListener
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}