package com.android.example.housingconnect

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FormLocationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<ImageButton>(R.id.back_form_location)
        back.setOnClickListener{
            activity?.onBackPressed()
        }

        var post = Post(
            0,
            "",
            "",
            0,
            0,
            0,
            "",
            "",
            "", //changes here
            "",
            "",
            ""
            )

        val user = Firebase.auth.currentUser
        post.email = user.email

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val formatted = formatter.format(time)
        post.date = formatted


        val address: TextInputLayout = view.findViewById(R.id.location1)
        val city: TextInputLayout = view.findViewById(R.id.city1)
        val state: TextInputLayout = view.findViewById(R.id.state1)

        val continueButton = view.findViewById<Button>(R.id.continueBtn1)
        continueButton.setOnClickListener{
            if (address.editText?.text.isNullOrBlank() || city.editText?.text.isNullOrBlank() || state.editText?.text.isNullOrBlank()) {
                Toast.makeText(context,"Missing information!",Toast.LENGTH_SHORT).show()
            }
            else {
                post.location = "${address.editText?.text}, ${city.editText?.text}, ${state.editText?.text}"
                val action = FormLocationFragmentDirections.actionFormLocationFragmentToFormDetailsFragment(post)
                findNavController().navigate(action)
            }
        }

    }
}