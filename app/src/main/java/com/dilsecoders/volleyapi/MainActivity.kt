package com.dilsecoders.volleyapi

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var currentImageUrl: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()

    }

    private fun loadMeme(){
        progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"
        val MySingleton = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)
            },
            { error ->
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_LONG).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        MySingleton.add(jsonObjectRequest)
    }

    fun next(view: View){
        loadMeme()
    }

    fun share(view: View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, check out this cool meme I found on Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share This Meme Using:")
        startActivity(chooser)
    }
}