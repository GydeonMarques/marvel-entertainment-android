package br.com.android.marvel.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object LoadImageUtils {

    /**
     * Loads the image from the internet
     *
     * @param context
     * @param imageView
     * @param animationView
     * @param viewAfterLoading
     * @param imageURL
     * @param imageDefault
     */
    fun loadImageAndApplyToImageView(context: Context?, imageView: ImageView?, imageURL: String?, imageDefault: Drawable?) {
        if (!imageURL.isNullOrEmpty()) {
            try {
                GlideApp.with(context!!).load(imageURL)
                    .error(imageDefault)
                    .placeholder(imageDefault)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.NORMAL)
                    .encodeQuality(100)
                    .dontTransform()
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            imageView!!.setImageDrawable(imageDefault)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    }).into(imageView!!)

            } catch (e: Exception) {
                GlideApp.with(context!!).load(imageDefault).into(imageView!!)
            }

        } else {
            try {
                if (imageView != null) {
                    GlideApp.with(context!!).load(imageDefault).into(imageView)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}