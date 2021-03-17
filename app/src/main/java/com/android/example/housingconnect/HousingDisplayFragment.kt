package com.android.example.housingconnect

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_housing_display.*
import java.util.*


class HousingDisplayFragment : Fragment() {

    private val args: HousingDisplayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_housing_display, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<ImageButton>(R.id.back_housing_view)
        back.setOnClickListener{
            activity?.onBackPressed()
        }

        val housingType: TextView = view.findViewById(R.id.housingType1)
        val location: TextView = view.findViewById(R.id.address1)
        val city: TextView = view.findViewById(R.id.city1)
        val price: TextView = view.findViewById(R.id.price1)
        val numOfBeds: TextView = view.findViewById(R.id.numOfBeds1)
        val numOfBaths: TextView = view.findViewById(R.id.numOfBaths1)
        val covidTested: TextView = view.findViewById(R.id.covidTested)
        val covidTestedIcon: ImageView = view.findViewById(R.id.covidTestedIcon1)
        val dateListing: TextView = view.findViewById(R.id.listDate)
        val moveInDate: TextView = view.findViewById(R.id.moveInDate)
        val descriptionText: TextView = view.findViewById(R.id.description)

        val post = args.post

        Glide.with(view)
            .load("https://Project-3-RentWell-Server.ryynison.repl.co/" + post.image)
            .into(housingImage2)

        housingType.text = post.type.toUpperCase(Locale.ROOT)

        val fullAddress = post.location.split(" ")
        val cityExtract: String = fullAddress[fullAddress.size-2]+" "+fullAddress[fullAddress.size-1]
        var addressExtract = ""
        for(i in 0..(fullAddress.size-3)) {
            addressExtract += if(i != fullAddress.size-3){
                fullAddress[i]+" "
            } else {
                fullAddress[i]
            }
        }
        location.text = addressExtract.substring(0, addressExtract.length -1)
        city.text = cityExtract

        price.text = "$"+post.price.toString()
        numOfBeds.text = post.bed.toString()
        numOfBaths.text = post.bath.toString()

        covidTested.text = "Tested"
        if(post.covidTested != "true"){
            covidTestedIcon.setBackgroundResource(R.drawable.ic_baseline_add_circle_outline_24)
            covidTestedIcon.rotation = 45f
        } else {
            covidTestedIcon.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24)
        }

        dateListing.text = "Listing posted on ${post.date}"
        moveInDate.text = post.moveIn
        descriptionText.text = post.desc


        val emailButton = view.findViewById<Button>(R.id.emailBtn)
        emailButton.setOnClickListener{
            val emailRecipient = post.email
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailRecipient))
            try {
                startActivity(Intent.createChooser(emailIntent, "Choose an email client."))
            } catch (e: Exception) {
                Log.e("DisplayFragment", "Failed to open email client.")
            }

        }

    }
}