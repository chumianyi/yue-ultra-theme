package com.yue.ultra.theme.widget

import android.content.Context
import android.graphics.*
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatEditText
import com.yue.ultra.theme.YueThemeConfig
import com.yue.ultra.theme.effect.YueGlowEffect

/**
 * 极致Yue Ultra 透明输入框
 * 表面泛光，2048x级别光影效果
 */
class YueEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val glowEffect = YueGlowEffect()
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shinePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var cornerRadius = 24f
    private var borderWidth = 2f
    private var shineProgress = 0f
    private var isFocusedState = false
    private var animationRunnable: Runnable? = null

    init {
        setupEditText()
    }

    private fun setupEditText() {
        // 透明背景
        setBackgroundColor(Color.TRANSPARENT)
        setPadding(48, 32, 48, 32)
        gravity = Gravity.CENTER_VERTICAL
        textSize = 16f
        setTextColor(Color.WHITE)
        setHintTextColor(YueThemeConfig.withAlpha(Color.WHITE, 0.5f))

        // 光标颜色
        try {
            val cursorDrawable = android.graphics.drawable.GradientDrawable()
            cursorDrawable.setColor(YueThemeConfig.glowColor)
            cursorDrawable.setSize(4, 40)
            val field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            field.isAccessible = true
        } catch (e: Exception) {
            // ignore
        }

        // 焦点监听
        setOnFocusChangeListener { _, hasFocus ->
            isFocusedState = hasFocus
            if (hasFocus) {
                startShineAnimation()
            } else {
                stopShineAnimation()
            }
            invalidate()
        }

        // 文字变化监听
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                invalidate()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        val width = width
        val height = height

        if (width > 0 && height > 0) {
            // 绘制透明光晕背景
            glowEffect.drawGlowBackground(canvas, width, height, cornerRadius)

            // 绘制渐变边框
            if (isFocusedState) {
                glowEffect.drawGlowBorder(canvas, width, height, cornerRadius, borderWidth * 1.5f)
            } else {
                glowEffect.drawGlowBorder(canvas, width, height, cornerRadius, borderWidth)
            }

            // 绘制内部阴影
            glowEffect.drawInnerShadow(canvas, width, height, cornerRadius)

            // 绘制动画光泽
            if (isFocusedState) {
                glowEffect.drawAnimatedShine(canvas, width, height, cornerRadius, shineProgress)
            }
        }

        super.onDraw(canvas)
    }

    private fun startShineAnimation() {
        stopShineAnimation()
        shineProgress = 0f
        animationRunnable = object : Runnable {
            override fun run() {
                shineProgress += 0.02f
                if (shineProgress > 1f) {
                    shineProgress = 0f
                }
                invalidate()
                postDelayed(this, 16)
            }
        }
        post(animationRunnable)
    }

    private fun stopShineAnimation() {
        animationRunnable?.let { removeCallbacks(it) }
        animationRunnable = null
        shineProgress = 0f
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    fun setBorderWidth(width: Float) {
        borderWidth = width
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopShineAnimation()
    }
}
