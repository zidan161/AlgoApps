package com.zidan.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.text.TextUtils
import android.widget.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.math.exp

class DetailActivity : AppCompatActivity() {

    private lateinit var imageBig: ImageView
    private lateinit var tvName: TextView
    private lateinit var btnImage: Button
    private lateinit var btnText: Button
    private lateinit var editText: EditText
    private lateinit var btnDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageBig = findViewById(R.id.img_big)
        tvName = findViewById(R.id.tv_name)
        btnImage = findViewById(R.id.btn_image)
        btnText = findViewById(R.id.btn_text)

        val extra = intent.extras
        val img = extra?.getString("img")

        Picasso.get().load(img).into(imageBig)

        btnImage.setOnClickListener {
            val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intentGallery, 101)
        }

        btnText.setOnClickListener {
            alert {
                customView{
                    linearLayout {
                        lparams {
                            orientation = LinearLayout.VERTICAL
                        }
                        editText = editText {
                            id = R.id.edt_text
                            inputType = InputType.TYPE_CLASS_TEXT
                        }
                        btnDone = button {
                            id = R.id.btn_done
                            text = "selesai"
                        }.lparams(width = wrapContent, height = wrapContent)
                    }
                }
            }.show()
            btnDone.setOnClickListener {
                val name = editText.text
                if (TextUtils.isEmpty(name)){
                    editText.error = "Field Harus diisi!"
                }
                tvName.text = name
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK && data != null){

            Picasso.get().load(data.dataString).centerInside().into(imageBig)
        }
    }
}