package com.alirsyad.app.component.radiobutton

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatRadioButton
import com.alirsyad.app.R
import com.alirsyad.app.databinding.LayoutDibRadioButtonBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

// TODO: If you are using androidx
// TODO: If you are using appcompat
//import android.support.annotation.Nullable;
//import android.support.v7.widget.AppCompatRadioButton;
class DibRadioButton(
    context: Context,
    attributeSet: AttributeSet? = null,
) : AppCompatRadioButton(context, attributeSet) {

    private var titleRadioText: String? = null

    private val binding: LayoutDibRadioButtonBinding = LayoutDibRadioButtonBinding.inflate(
        LayoutInflater.from(context), null, false
    )

    init {
        attributeSet?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.DibRadioButton,
                0,
                0
            ).apply {
                try {
                    titleRadioText = getString(R.styleable.DibRadioButton_titleRadioText)
                } finally {
                    //redrawLayout()
                    recycle()
                }
            }
        }
    }

    private val requestListener: RequestListener<Bitmap?> = object : RequestListener<Bitmap?> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any,
            target: Target<Bitmap?>,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any,
            target: Target<Bitmap?>,
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean {
            binding.ivAnswer.setImageBitmap(resource)
            redrawLayout()
            return false
        }
    }

    fun setImageResource(resId: Int) {
        Glide.with(context)
            .asBitmap()
            .load(resId)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(
                            dp2px(context, 24),
                            0,
                            RoundedCornersTransformation.CornerType.ALL
                        )
                    )
                )
            )
            .listener(requestListener)
            .submit()
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        Glide.with(context)
            .asBitmap()
            .load(bitmap)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCornersTransformation(
                            dp2px(context, 24),
                            0,
                            RoundedCornersTransformation.CornerType.ALL
                        )
                    )
                )
            )
            .listener(requestListener)
            .submit()
    }

    fun setTextWith(text: CharSequence?) = with(binding) {
        tvTitle.text = text.toString()
        //redrawLayout()
    }

    private fun redrawLayout() = with(binding){
        ivAnswer.isDrawingCacheEnabled = true
        ivAnswer.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        ivAnswer.layout(0, 0, ivAnswer.measuredWidth, ivAnswer.measuredHeight)
        ivAnswer.buildDrawingCache(true)
        val bitmap = Bitmap.createBitmap(ivAnswer.drawingCache)
        setCompoundDrawablesWithIntrinsicBounds(BitmapDrawable(resources, bitmap), null, null, null)
        ivAnswer.isDrawingCacheEnabled = false
    }

    private fun dp2px(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}