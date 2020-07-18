package br.com.android.marvel.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

    protected fun setUpToolbar(toolbar: Toolbar, title: String, showBackButton: Boolean = false) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        toolbar.setNavigationOnClickListener { finish() }
    }
}