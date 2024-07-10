package com.busanit501.flowertest

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    lateinit var cls: ClassifierWithModel
    lateinit var morebtn: Button
    lateinit var resultimg: ImageView
    lateinit var resulttxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        cls = ClassifierWithModel(this@ResultActivity)
        try {
            cls.init()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        morebtn = findViewById(R.id.restart_btn)
        resultimg = findViewById(R.id.result_img)
        resulttxt = findViewById(R.id.result_txt)

        val byteArray = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

        if (bitmap != null) {
            Log.d("lsy","이미지가 널이 아님")
            val (first, second) = cls.classify(bitmap)
            val resultStr: String =
                java.lang.String.format(Locale.ENGLISH, "%s", first)
            resultimg.setImageBitmap(bitmap)
            val result111 : String
            resulttxt.text = resultStr + "\n" + String.format("%.2f", second * 100) + "%!!"
            result111 = resultStr + "\n" + String.format("%.2f", second * 100) + "%!!"
            Log.d("lsy","결과 확인 : $result111")
        }

        morebtn.setOnClickListener {
            val intent = Intent(applicationContext, InputActivity::class.java)
            finish()
            cls.finish()
            startActivity(intent)
        }
    }
}