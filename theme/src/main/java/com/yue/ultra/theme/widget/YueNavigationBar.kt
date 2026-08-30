package com.yue.ultra.theme.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.yue.ultra.theme.YueThemeConfig
import com.yue.ultra.theme.effect.YueGlowEffect

/**
 * 极致Yue Ultra 透明导航栏
 * 透明背景，表面泛光
 */
class YueNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val glowEffect = YueGlowEffect()
    private val itemContainer = LinearLayout(context)
    private var cornerRadius = 28f
    private var selectedIndex = 0
    private var items = mutableListOf<NavItem>()
    private var onItemClickListener: ((Int) -> Unit)? = null

    data class NavItem(
        val title: String,
        val icon: Int? = null
    )

    init {
        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setBackgroundColor(Color.TRANSPARENT)
        setPadding(16, 12, 16, 12)
        elevation = 8f

        itemContainer.orientation = HORIZONTAL
        itemContainer.gravity = Gravity.CENTER_VERTICAL
        itemContainer.layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)

        addView(itemContainer)

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        val width = width
        val height = height

        if (width > 0 && height > 0) {
            // 绘制透明光晕背景
            glowEffect.drawGlowBackground(canvas, width, height, cornerRadius)

            // 绘制渐变边框
            glowEffect.drawGlowBorder(canvas, width, height, cornerRadius, 2f)

            // 绘制选中项指示器
            drawSelectedIndicator(canvas)
        }

        super.onDraw(canvas)
    }

    private fun drawSelectedIndicator(canvas: Canvas) {
        if (items.isEmpty() || selectedIndex >= items.size) return

        val itemWidth = (width - 32) / items.size
        val indicatorLeft = 16f + selectedIndex * itemWidth + 8f
        val indicatorRight = indicatorLeft + itemWidth - 16f
        val indicatorTop = height / 2f - 28f
        val indicatorBottom = height / 2f + 28f

        val indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        indicatorPaint.color = YueThemeConfig.withAlpha(YueThemeConfig.glowColor, 0.4f)
        indicatorPaint.style = Paint.Style.FILL

        val rect = RectF(indicatorLeft, indicatorTop, indicatorRight, indicatorBottom)
        canvas.drawRoundRect(rect, 20f, 20f, indicatorPaint)

        // 指示器边框
        indicatorPaint.style = Paint.Style.STROKE
        indicatorPaint.strokeWidth = 2f
        indicatorPaint.color = YueThemeConfig.withAlpha(YueThemeConfig.primaryLightColor, 0.8f)
        canvas.drawRoundRect(rect, 20f, 20f, indicatorPaint)
    }

    fun setItems(navItems: List<NavItem>) {
        items.clear()
        items.addAll(navItems)
        itemContainer.removeAllViews()

        items.forEachIndexed { index, item ->
            val itemView = createNavItemView(item, index)
            itemContainer.addView(itemView)
        }

        invalidate()
    }

    private fun createNavItemView(item: NavItem, index: Int): View {
        val itemLayout = LinearLayout(context)
        itemLayout.orientation = VERTICAL
        itemLayout.gravity = Gravity.CENTER
        itemLayout.layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)

        val titleView = TextView(context)
        titleView.text = item.title
        titleView.setTextColor(Color.WHITE)
        titleView.textSize = 13f
        titleView.gravity = Gravity.CENTER
        titleView.setPadding(8, 8, 8, 8)

        itemLayout.addView(titleView)

        itemLayout.setOnClickListener {
            selectedIndex = index
            onItemClickListener?.invoke(index)
            invalidate()
        }

        return itemLayout
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun setSelectedIndex(index: Int) {
        selectedIndex = index
        invalidate()
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }
}
