package com.android.example.housingconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout

class FormDetailsFragment : Fragment() {

    private val args: FormDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<ImageButton>(R.id.back_form_details)
        back.setOnClickListener{
            activity?.onBackPressed()
        }

        var post = args.post

        val housingType: TextInputLayout = view.findViewById(R.id.housingType1)
        val numBedrooms: TextInputLayout = view.findViewById(R.id.bedrooms1)
        val numBathrooms: TextInputLayout = view.findViewById(R.id.bathrooms1)
        val rentPrice: TextInputLayout = view.findViewById(R.id.rent1)
        val moveInDate: TextInputLayout = view.findViewById(R.id.movein1)

        val continueButton = view.findViewById<Button>(R.id.continueBtn2)
        continueButton.setOnClickListener{
            if (
                housingType.editText?.text.toString().isNullOrBlank() ||
                numBedrooms.editText?.text.toString().isNullOrBlank() ||
                numBathrooms.editText?.text.toString().isNullOrBlank() ||
                rentPrice.editText?.text.toString().isNullOrBlank() ||
                moveInDate.editText?.text.toString().isNullOrBlank()
            ) {
                Toast.makeText(context,"Missing information!",Toast.LENGTH_SHORT).show()
            }
            else {
                post.type = housingType.editText?.text.toString()
                post.bed = Integer.parseInt(numBedrooms.editText?.text.toString())
                post.bath = Integer.parseInt(numBathrooms.editText?.text.toString())
                post.price = Integer.parseInt(rentPrice.editText?.text.toString())
                post.moveIn = moveInDate.editText?.text.toString()
                val action = FormDetailsFragmentDirections.actionFormDetailsFragmentToFormDescriptionFragment(post)
                findNavController().navigate(action)
            }

        }
    }
}