package br.com.android.marvel.ui.splash

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.ui.movies.MoviesActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startAnimations()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window?.setFormat(PixelFormat.RGBA_8888)
        window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun startAnimations() {
        layout_splash?.clearAnimation()
        layout_splash?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha))

        Handler().postDelayed({
            startActivity(Intent(this, MoviesActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        //It is not allowed to return at this point
    }
}
