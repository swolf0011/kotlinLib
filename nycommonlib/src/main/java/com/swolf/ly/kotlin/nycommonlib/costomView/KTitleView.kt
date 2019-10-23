package com.swolf.ly.kotlin.nycommonlib.costomView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.swolf.ly.kotlin.nycommonlib.R

/**
 * 标题
 * xmlns:nyapp="http://schemas.android.com/apk/res-auto
 */
class KTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    internal var imageView_left_resource: Int = 0
    internal var textView_left_text: String? = null
    internal var textView_left_textsize: Float = 0.toFloat()
    internal var textView_left_textcolor: Int = 0
    internal var textView_title_text: String? = null
    internal var textView_title_textsize: Float = 0.toFloat()
    internal var textView_title_textcolor: Int = 0
    internal var textView_right_text: String? = null
    internal var textView_right_textsize: Float = 0.toFloat()
    internal var textView_right_textcolor: Int = 0
    internal var imageView_right_resource: Int = 0

    var imageViewLeft: ImageView
    var textViewLeft: TextView
    var textViewTitle: TextView
    var textViewRight: TextView
    var imageViewRight: ImageView

    internal var paint: Paint? = null


    init {

        LayoutInflater.from(context).inflate(R.layout.ny_view_title, this)
        imageViewLeft = findViewById(R.id.imageViewLeft)
        textViewLeft = findViewById(R.id.textViewLeft)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewRight = findViewById(R.id.textViewRight)
        imageViewRight = findViewById(R.id.imageViewRight)
    }

    fun setImageViewLeft(resource: Int): KTitleView {
        imageViewLeft.setImageResource(resource)
        return this
    }

    fun setImageViewLeft(padding: Int, resource: Int): KTitleView {
        imageViewLeft.setPadding(padding, padding, padding, padding)
        imageViewLeft.setImageResource(resource)
        return this
    }

    fun setTextViewLeft(text: String): KTitleView {
        textViewLeft.text = text
        return this
    }

    fun setTextViewLeft(textColor: Int, text: String): KTitleView {
        textViewLeft.setTextColor(textColor)
        textViewLeft.text = text
        return this
    }

    fun setTextViewLeft(textColor: Int, text: String, textSize: Float): KTitleView {
        textViewLeft.setTextColor(textColor)
        textViewLeft.text = text
        textViewLeft.textSize = textSize
        return this
    }

    fun setTextViewTitle(text: String): KTitleView {
        textViewTitle.text = text
        return this
    }

    fun setTextViewTitle(textColor: Int, text: String): KTitleView {
        textViewTitle.setTextColor(textColor)
        textViewTitle.text = text
        return this
    }

    fun setTextViewTitle(textColor: Int, text: String, textSize: Float): KTitleView {
        textViewTitle.setTextColor(textColor)
        textViewTitle.text = text
        textViewTitle.textSize = textSize
        return this
    }

    fun setTextViewRight(text: String): KTitleView {
        textViewRight.text = text
        return this
    }

    fun setTextViewRight(textColor: Int, text: String): KTitleView {
        textViewRight.setTextColor(textColor)
        textViewRight.text = text
        return this
    }

    fun setTextViewRight(textColor: Int, text: String, textSize: Float): KTitleView {
        textViewRight.setTextColor(textColor)
        textViewRight.text = text
        textViewRight.textSize = textSize
        return this
    }

    fun setImageViewRight(resource: Int): KTitleView {
        imageViewRight.setImageResource(resource)
        return this
    }

    fun setImageViewRight(padding: Int, resource: Int): KTitleView {
        imageViewRight.setPadding(padding, padding, padding, padding)
        imageViewRight.setImageResource(resource)
        return this
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }


    fun setSubViewsVisibility(
        imageViewLeftVisibility: Int,
        textViewLeftVisibility: Int,
        textViewRightVisibility: Int,
        imageViewRightVisibility: Int
    ): KTitleView {
        imageViewLeft.visibility = imageViewLeftVisibility
        textViewLeft.visibility = textViewLeftVisibility
        textViewRight.visibility = textViewRightVisibility
        imageViewRight.visibility = imageViewRightVisibility
        return this
    }


    fun setImageViewLeftClickListener(clickListener: View.OnClickListener): KTitleView {
        imageViewLeft.setOnClickListener(clickListener)
        return this
    }

    fun setTextViewLeftClickListener(clickListener: View.OnClickListener): KTitleView {
        textViewLeft.setOnClickListener(clickListener)
        return this
    }

    fun setTextViewTitleClickListener(clickListener: View.OnClickListener): KTitleView {
        textViewTitle.setOnClickListener(clickListener)
        return this
    }

    fun setTextViewRightClickListener(clickListener: View.OnClickListener): KTitleView {
        textViewRight.setOnClickListener(clickListener)
        return this
    }

    fun setImageViewRightClickListener(clickListener: View.OnClickListener): KTitleView {
        imageViewRight.setOnClickListener(clickListener)
        return this
    }
}
