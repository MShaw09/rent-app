package com.android.example.housingconnect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_form_image.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormImageFragment : Fragment() {

    var imageUri: Uri? = null
    private val args: FormImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postButton = view.findViewById<Button>(R.id.uploadImageButton)
        postButton?.setOnClickListener{
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2020)
        }

        val back = view.findViewById<ImageButton>(R.id.back_form_image)
        back.setOnClickListener{
            activity?.onBackPressed()
        }

        val doneButton: Button = view.findViewById(R.id.submitBtnToHome)
        doneButton.setOnClickListener{
            postImage()
        }

    }

    fun postImage() {
        if (imageUri == null) {
            return
        }

        val contentResolver = requireContext().contentResolver
        val bytes = contentResolver.openInputStream(imageUri!!)!!.readBytes()
        val extension = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(imageUri!!))

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes)
        val part = MultipartBody.Part.createFormData("image", "image.$extension", requestBody)

        var post = args.post
        val service = (requireActivity() as MainActivity).housingService
        service.uploadImage(part).enqueue(object : Callback<ImageUploadResponse> {
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                Log.d("FormImageFragment", "Success in OnResponse")
                post.image = response.body()!!.path
                service.create(post).enqueue(object : Callback<Message> {
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        Log.d("FormImageFragment", "Success in OnResponse")
                        val action = R.id.action_formImageFragment_to_housingFeedFragment
                        findNavController().navigate(action)
                    }
                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.d("FormImageFragment", "Success in OnFailure")
                        Log.d("FormImageFragment", t.toString())
                    }
                })
            }
            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                Log.d("FormImageFragment", "Success in OnFailure")
                Log.d("FormImageFragment", t.toString())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2020) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data!!.data
                housingImage.setImageURI(imageUri)
            }
        }
    }
}