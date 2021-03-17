package com.android.example.housingconnect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_housing_feed.*
import retrofit2.Call
import retrofit2.Response

class HousingFeedFragment : Fragment() {

    private var postList = mutableListOf<Post>(
            Post(0,"a", "a", 1, 2, 3, "no", "yes","1580 Jade Street, Davis, CA", "yes", "yes", "/images/3396a6c84c40b492be3640cd865b4424" ),
            Post(0,"a", "a", 1, 2, 3, "true", "yes","1580 Jade Street, Davis, CA", "yes", "yes", "/images/ae5ec403b01e1ed23dfbff1206d1d65b" ))//mutableListOf<Post>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_housing_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val housingAdapter = HousingListAdapter(postList)
        val recyclerView: RecyclerView = housing_recycler_view
        housing_recycler_view.adapter = housingAdapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val postButton = view?.findViewById<Button>(R.id.postButton)
        postButton?.setOnClickListener{
            val user = Firebase.auth.currentUser
            if (user != null) {
                findNavController().navigate(R.id.action_housingFeedFragment_to_formLocationFragment)
            } else {
                findNavController().navigate(R.id.action_housingFeedFragment_to_signInFragment2)
            }
        }

        val housingService = (requireActivity() as MainActivity).housingService

        housingService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: retrofit2.Response<List<Post>>) {
                Log.d("HousingFeedFragment", "Success in onResponse")
                housingAdapter.setData(response.body()!!)
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("HousingFeedFragment", "Success in onFailure")
                Log.d("HousingFeedFragment", t.toString())}
        })
    }
}
