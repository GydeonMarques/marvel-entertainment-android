package br.com.android.marvel.ui.extensions

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import br.com.android.marvel.entertainment.R
import br.com.android.marvel.utils.LoadImageUtils

@BindingAdapter("app:loadImageByUrl")
fun ImageView.loadImageByUrl(imageUrl: String?) {
    imageUrl?.let { url ->
        LoadImageUtils.loadImageAndApplyToImageView(
            context,
            this,
            url,
            ContextCompat.getDrawable(context, R.drawable.image_default)
        )
    }
}
