package ir.sass.base_ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import ir.sass.base_ui.R
import ir.sass.base_ui.databinding.LottieImageViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LottieImageView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context,attrs,defStyleAttr){
    val binding : LottieImageViewBinding = LottieImageViewBinding.inflate(
        LayoutInflater.from(context),this,true
    )
}

@BindingAdapter("ext:url")
fun setImageUrl(img: LottieImageView, url: String?) {

    url?.let {
        img.binding.lottie.playAnimation()

        img.setBackgroundResource(R.drawable.back_img)

        Glide.with(img.context)
            .load(url)
            .centerCrop()
            .addListener(imageLoadingListener(img.binding.lottie,img.binding.img))
            .into(img.binding.img)
    }
}


fun imageLoadingListener(pendingImage: LottieAnimationView,img : ImageView): RequestListener<Drawable?> {
    return object : RequestListener<Drawable?> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>?, isFirstResource: Boolean): Boolean {
            pendingImage.pauseAnimation()
            pendingImage.visibility = View.GONE
            CoroutineScope(Main).launch {
                delay(30)
                img.setImageResource(R.drawable.broken_image)
            }
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Drawable?>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            pendingImage.pauseAnimation()
            pendingImage.visibility = View.GONE
            return false
        }
    }
}