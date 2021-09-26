package com.nikol.avatarview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import kotlin.math.min

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var radius: Float = 0.0f
    private var xCenter: Float = 0.0f
    private var yCenter: Float = 0.0f


    private var xCenterText: Float = 0.0f
    private var yCenterText: Float = 0.0f

    private var avatarText: String = ""

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = Color.BLACK
    }


    init {
        context.withStyledAttributes(
            attrs,
            R.styleable.AvatarView
        ) {
            val fullText = getString(R.styleable.AvatarView_av_text) ?: ""
            avatarText = getFirstCharsOfString(fullText)

            backgroundPaint.color = getColor(R.styleable.AvatarView_av_background, Color.WHITE)

            textPaint.color = getColor(R.styleable.AvatarView_av_textColor, Color.BLACK)
            textPaint.textSize = getDimension(R.styleable.AvatarView_av_textSize, 55.0f)
        }
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun getFirstCharsOfString(string: String): String {
        var initials = ""
        for (word in string.split(" ")) {
            initials += word.firstOrNull() ?: ""
        }
        return initials.take(2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(xCenter, yCenter, radius, backgroundPaint)
        canvas.drawText(avatarText, xCenterText, yCenterText, textPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = min(w, h) / 2.0F
        xCenter = w / 2.0f
        yCenter = h / 2.0f

        xCenterText = xCenter
        yCenterText = yCenter - (textPaint.descent() + textPaint.ascent() / 2)
    }
}