package com.zidan.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareButton
import com.squareup.picasso.Picasso
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class ShareActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imgShare: ImageView
    private lateinit var btnFacebook: ShareButton
    private lateinit var btnTwitter: Button
    private var image: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        FacebookSdk.sdkInitialize(applicationContext)

        imgShare = findViewById(R.id.img_share)
        btnFacebook = findViewById(R.id.btn_share_facebook)
        btnTwitter = findViewById(R.id.btn_share_twitter)

        btnFacebook.setOnClickListener(this)
        btnTwitter.setOnClickListener(this)

        image = intent.extras?.getString("img")
        Picasso.get().load(image).into(imgShare)

        imgShare.isDrawingCacheEnabled = true

        val photo = SharePhoto.Builder()
                .setBitmap(imgShare.drawingCache)
                .build()
        val content = SharePhotoContent.Builder()
                .addPhoto(photo)
                .build()
        btnFacebook.shareContent = content
    }

    override fun onClick(view: View?) {
    }
}