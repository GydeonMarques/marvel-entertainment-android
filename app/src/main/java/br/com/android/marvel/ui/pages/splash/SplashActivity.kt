package br.com.android.marvel.ui.pages.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.entertainment.databinding.ActivitySplashBinding
import br.com.android.marvel.ui.pages.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        startAnimations()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        with(window) {
            this?.setFormat(PixelFormat.RGBA_8888)
            this?.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun startAnimations() {
        with(binding) {
            layoutSplash.apply {
                clearAnimation()
                startAnimation(AnimationUtils.loadAnimation(this@SplashActivity, R.anim.alpha))
            }
        }

        //Simulação de algum tipo de pré carregamento de dados
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        //It is not allowed to return at this point
    }
}
