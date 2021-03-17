package com.android.example.housingconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout

class FormDescriptionFragment : Fragment() {

    private val args: FormDescriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<ImageButton>(R.id.back_form_desc)
        back.setOnClickListener{
            activity?.onBackPressed()
        }

        var post = args.post

        val description: TextInputLayout = view.findViewById(R.id.description1)
        val covidCheck: CheckBox = view.findViewById(R.id.covidTestedCheck1)

        val continueButton = view.findViewById<Button>(R.id.continueBtn3)
        continueButton.setOnClickListener{
            post.desc = description.editText?.text.toString()
            if(covidCheck.isChecked){
                post.covidTested = "true"
            } else {
                post.covidTested = "false"
            }
            val action = FormDescriptionFragmentDirections.actionFormDescriptionFragmentToFormImageFragment(post)
            findNavController().navigate(action)
        }
    }
}