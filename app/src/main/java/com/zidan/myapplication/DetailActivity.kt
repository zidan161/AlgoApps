package com.zidan.myapplication

import android.content.*
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.*
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import java.io.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imageBig: ImageView
    private lateinit var tvName: TextView
    private lateinit var btnImage: ImageButton
    private lateinit var btnText: ImageButton
    private lateinit var btnSave: Button
    private lateinit var btnShare: Button
    private var image: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageBig = findViewById(R.id.img_big)
        tvName = findViewById(R.id.tv_name)
        btnImage = findViewById(R.id.btn_image)
        btnText = findViewById(R.id.btn_text)
        btnSave = findViewById(R.id.btn_save)
        btnShare = findViewById(R.id.btn_share)

        btnImage.setOnClickListener(this)
        btnText.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnShare.setOnClickListener(this)

        val extra = intent.extras
        image = extra?.getString("img")

        Picasso.get().load(image).into(imageBig)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK && data != null){

            Picasso.get().load(data.dataString).centerInside().into(imageBig)
        }
    }

    override fun onClick(view: View) {
        when(view){
            btnImage -> {
                val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intentGallery, 101)
            }
            btnText -> {
                alert {
                    customView {
                        linearLayout {
                            lparams {
                                orientation = LinearLayout.VERTICAL
                            }
                            val editText = editText {
                                id = R.id.edt_text
                                inputType = InputType.TYPE_CLASS_TEXT
                            }
                            button {
                                id = R.id.btn_done
                                text = "selesai"
                                this.setOnClickListener {
                                    val name = editText.text
                                    if (TextUtils.isEmpty(name)) {
                                        editText.error = "Field Harus diisi!"
                                    }
                                    tvName.text = name
                                }
                                applyRecursively {
                                    R.style.TextAppearance_AppCompat_Widget_Button_Colored
                                }
                            }.lparams(width = wrapContent, height = wrapContent)
                        }
                    }
                }.show()
            }
            btnSave -> {
                val cw = ContextWrapper(applicationContext)
                val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
                val mypath = File(directory, "save.jpg")

                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(mypath)

                    val bitmapImage = imageBig.drawable.toBitmap()

                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    fos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            btnShare -> {
                startActivity<ShareActivity>("img" to image)
            }
        }
    }
}