package com.yue.ultra.theme.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import com.yue.ultra.theme.YueThemeConfig
import com.yue.ultra.theme.effect.YueGlowEffect

/**
 * 极致Yue Ultra 透明选择框
 * 透明背景，更加绚丽的光影效果
 */
class YueSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatSpinner(context, attrs, defStyleAttr) {

    private val glowEffect = YueGlowEffect()
    private var cornerRadius = 20f
    private var borderWidth = 2f
    private var arrowRotation = 0f
    private var isPopupShowing = false

    init {
        setupSpinner()
    }

    private fun setupSpinner() {
        setBackgroundColor(Color.TRANSPARENT)
        setPadding(48, 24, 64, 24)
        gravity = Gravity.CENTER_VERTICAL

        // 自定义下拉箭头
        setBackgroundResource(0)
    }

    override fun onDraw(canvas: Canvas) {
        val width = width
        val height = height

        if (width > 0 && height > 0) {
            // 绘制绚丽光晕背景
            glowEffect.drawGlowBackground(canvas, width, height, cornerRadius)

            // 绘制渐变边框
            glowEffect.drawGlowBorder(canvas, width, height, cornerRadius, borderWidth)

            // 绘制内部阴影
            glowEffect.drawInnerShadow(canvas, width, height, cornerRadius)

            // 绘制下拉箭头
            drawArrow(canvas, width, height)

            // 如果弹出框显示，绘制额外光晕
            if (isPopupShowing) {
                val extraGlow = Paint(Paint.ANTI_ALIAS_FLAG)
                extraGlow.color = YueThemeConfig.withAlpha(YueThemeConfig.glowColor, 0.3f)
                extraGlow.style = Paint.Style.FILL
                val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, extraGlow)
            }
        }

        super.onDraw(canvas)
    }

    private fun drawArrow(canvas: Canvas, width: Int, height: Int) {
        val arrowSize = 24f
        val arrowX = width - arrowSize - 24f
        val arrowY = height / 2f

        val arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        arrowPaint.color = Color.WHITE
        arrowPaint.style = Paint.Style.STROKE
        arrowPaint.strokeWidth = 3f
        arrowPaint.strokeCap = Paint.Cap.ROUND

        canvas.save()
        canvas.translate(arrowX, arrowY)
        canvas.rotate(arrowRotation)

        val path = Path()
        path.moveTo(-arrowSize / 2, -arrowSize / 4)
        path.lineTo(0f, arrowSize / 4)
        path.lineTo(arrowSize / 2, -arrowSize / 4)
        canvas.drawPath(path, arrowPaint)

        canvas.restore()
    }

    override fun performClick(): Boolean {
        isPopupShowing = true
        arrowRotation = 180f
        invalidate()
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            isPopupShowing = false
            arrowRotation = 0f
            invalidate()
        }
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    fun setBorderWidth(width: Float) {
        borderWidth = width
        invalidate()
    }

    /**
     * 创建Yue风格的下拉适配器
     */
    fun <T> setYueAdapter(items: List<T>, onItemSelected: (T, Int) -> Unit = { _, _ -> }) {
        val adapter = object : ArrayAdapter<T>(context, android.R.layout.simple_spinner_item, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(Color.WHITE)
                view.textSize = 15f
                view.setPadding(0, 0, 0, 0)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setBackgroundColor(YueThemeConfig.withAlpha(YueThemeConfig.primaryColor, 0.9f))
                view.setTextColor(Color.WHITE)
                view.textSize = 15f
                view.setPadding(48, 24, 48, 24)
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        setAdapter(adapter)

        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                isPopupShowing = false
                arrowRotation = 0f
                invalidate()
                onItemSelected(items[position], position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
