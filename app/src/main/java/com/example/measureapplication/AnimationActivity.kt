package com.example.measureapplication

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_animation.*
import kotlin.concurrent.timer

class AnimationActivity : AppCompatActivity() {

    override fun onBackPressed() {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        imageView.setImageResource(R.drawable.loading)
        //var drawable:AnimatedVectorDrawable = imageView.drawable as AnimatedVectorDrawable
        val drawable = imageView.drawable
        if (drawable is AnimatedVectorDrawable)
            drawable.start()

        //val intent = Intent(this, selectCategoryActivity::class.java)
        val handler = Handler()
        Handler().postDelayed(Runnable {
            val intent = Intent(this, selectCategoryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 4500)

        //Now loading
        loadingText.text = "Now Loading.."
        ObjectAnimator.ofFloat(loadingText, "alpha", 1.0f, 0.0f).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            duration = 500
            start()

        }
    }
}
