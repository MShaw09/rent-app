package com.android.example.housingconnect

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class HousingListAdapter(private var posts: List<Post>) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.housing_list_item, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = posts[position]
        holder.apply {
            housingType.text = item.type.toUpperCase(Locale.ROOT)

            val fullAddress = item.location.split(" ")
            val cityExtract = fullAddress[fullAddress.size-2]+" "+fullAddress[fullAddress.size-1]
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
            price.text = "$"+item.price.toString()
            numOfBeds.text = item.bed.toString()
            numOfBaths.text = item.bath.toString()

            covidTested.text = "Tested"
            if(item.covidTested != "true"){
                covidTestedIcon.setBackgroundResource(R.drawable.ic_baseline_add_circle_outline_24)
                covidTestedIcon.rotation = 45f
            } else {
                covidTestedIcon.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24)
            }

             Glide.with(holder.itemView)
                     .load("https://Project-3-RentWell-Server.ryynison.repl.co/" + item.image)
                     .into(housingImage)
        }

        holder.housingItem.setOnClickListener{
            val action = HousingFeedFragmentDirections.actionHousingFeedFragmentToHousingDisplayFragment(item)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = posts.size

    fun setData(newPosts: List<Post>) {
        posts = newPosts
        this.notifyDataSetChanged()
    }
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val housingItem: ConstraintLayout = view.findViewById(R.id.housingItem)
    val housingImage: ImageView = view.findViewById(R.id.housingImage)
    val housingType: TextView = view.findViewById(R.id.housingType)
    val location: TextView = view.findViewById(R.id.address)
    val city: TextView = view.findViewById(R.id.city)
    val price: TextView = view.findViewById(R.id.price)
    val numOfBeds: TextView = view.findViewById(R.id.numOfBeds)
    val numOfBaths: TextView = view.findViewById(R.id.numOfBaths)
    val covidTested: TextView = view.findViewById(R.id.covidTested)
    var covidTestedIcon: ImageView = view.findViewById(R.id.covidTestedIcon)
}