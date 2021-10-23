package com.bootcampteamc.odev.ui.home.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bootcampteamc.odev.R
import com.bootcampteamc.odev.databinding.FragmentSearchBinding
import com.bootcampteamc.odev.infra.BaseFragment
import com.bootcampteamc.odev.infra.BaseViewModel
import com.bootcampteamc.odev.ui.home.adapter.SearchProductAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class SearchFragment() : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val db: FirebaseFirestore by lazy {  FirebaseFirestore.getInstance()}
    private lateinit var adapter: SearchProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvSearch.setHasFixedSize(true)
        setupRecyclerView()
        binding.rvSearch.adapter=adapter
        setupTextChangedListener()

        return binding.root
    }


    private fun setupTextChangedListener(){
        binding.search.addTextChangedListener (object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loadFirebaseData(binding.search.text.toString().trim())
            }

            override fun afterTextChanged(p0: Editable?) {
            }


        } )
    }


    private fun setupRecyclerView(){
        val query : Query = db.collection("test").orderBy("name")
        adapter=SearchProductAdapter(query)
    }
    private fun loadFirebaseData(searchText : String) {
        val firebaseSearchQuery = db.collection("test").orderBy("name").startAt(searchText).endAt(searchText + "\uf8ff")
        adapter= SearchProductAdapter(firebaseSearchQuery)
        binding.rvSearch.adapter=adapter

    }

}