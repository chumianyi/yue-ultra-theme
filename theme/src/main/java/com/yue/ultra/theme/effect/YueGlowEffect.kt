package com.yue.ultra.theme.effect

import android.graphics.*
import android.view.View
import com.yue.ultra.theme.YueThemeConfig

/**
 * 极致Yue Ultra 光影效果引擎
 * 2048x级别光影渲染，表面泛光效果
 */
class YueGlowEffect {

    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var glowShader: RadialGradient? = null
    private var shineShader: LinearGradient? = null
    private var borderShader: LinearGradient? = null

    private var lastWidth = 0
    private var lastHeight = 0
    private var animationProgress = 0f

    init {
        glowPaint.isDither = true
        shinePaint.isDither = true
        borderPaint.isDither = true
        shadowPaint.isDither = true
    }

    fun updateShaders(width: Int, height: Int) {
        if (width == lastWidth && height == lastHeight) return
        lastWidth = width
        lastHeight = height

        val primary = YueThemeConfig.primaryColor
        val glow = YueThemeConfig.glowColor
        val light = YueThemeConfig.primaryLightColor

        // 2048x级别径向光晕
        glowShader = RadialGradient(
            width / 2f, height / 2f,
            Math.max(width, height).toFloat(),
            intArrayOf(
                YueThemeConfig.withAlpha(glow, 0.4f),
                YueThemeConfig.withAlpha(primary, 0.2f),
                YueThemeConfig.withAlpha(light, 0.1f),
                Color.TRANSPARENT
            ),
            floatArrayOf(0f, 0.3f, 0.6f, 1f),
            Shader.TileMode.CLAMP
        )

        // 表面光泽线性渐变
        shineShader = LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            intArrayOf(
                YueThemeConfig.withAlpha(Color.WHITE, 0.25f),
                YueThemeConfig.withAlpha(Color.WHITE, 0.05f),
                YueThemeConfig.withAlpha(glow, 0.15f),
                YueThemeConfig.withAlpha(Color.WHITE, 0.1f)
            ),
            floatArrayOf(0f, 0.3f, 0.7f, 1f),
            Shader.TileMode.CLAMP
        )

        // 边框渐变
        borderShader = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            intArrayOf(
                YueThemeConfig.withAlpha(glow, 0.8f),
                YueThemeConfig.withAlpha(light, 0.6f),
                YueThemeConfig.withAlpha(primary, 0.7f),
                YueThemeConfig.withAlpha(glow, 0.8f)
            ),
            floatArrayOf(0f, 0.33f, 0.66f, 1f),
            Shader.TileMode.CLAMP
        )
    }

    fun drawGlowBackground(canvas: Canvas, width: Int, height: Int, cornerRadius: Float) {
        updateShaders(width, height)

        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())

        // 绘制光晕背景
        glowPaint.shader = glowShader
        glowPaint.alpha = (YueThemeConfig.glowIntensity * 255).toInt()
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, glowPaint)

        // 绘制表面光泽
        shinePaint.shader = shineShader
        shinePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, shinePaint)
        shinePaint.xfermode = null
    }

    fun drawGlowBorder(canvas: Canvas, width: Int, height: Int, cornerRadius: Float, strokeWidth: Float = 3f) {
        updateShaders(width, height)

        borderPaint.shader = borderShader
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = strokeWidth
        borderPaint.alpha = 200

        val rect = RectF(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2)
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, borderPaint)
    }

    fun drawInnerShadow(canvas: Canvas, width: Int, height: Int, cornerRadius: Float) {
        val shadowRect = RectF(4f, 4f, width - 4f, height - 4f)
        shadowPaint.shader = null
        shadowPaint.color = YueThemeConfig.withAlpha(YueThemeConfig.primaryDarkColor, 0.15f)
        shadowPaint.style = Paint.Style.STROKE
        shadowPaint.strokeWidth = 2f
        canvas.drawRoundRect(shadowRect, cornerRadius - 2f, cornerRadius - 2f, shadowPaint)
    }

    fun drawAnimatedShine(canvas: Canvas, width: Int, height: Int, cornerRadius: Float, progress: Float) {
        animationProgress = progress
        val shineWidth = width * 0.3f
        val shineX = -shineWidth + progress * (width + shineWidth * 2)

        val shineGradient = LinearGradient(
            shineX, 0f, shineX + shineWidth, height.toFloat(),
            intArrayOf(
                Color.TRANSPARENT,
                YueThemeConfig.withAlpha(Color.WHITE, 0.4f),
                YueThemeConfig.withAlpha(YueThemeConfig.glowColor, 0.3f),
                Color.TRANSPARENT
            ),
            floatArrayOf(0f, 0.4f, 0.6f, 1f),
            Shader.TileMode.CLAMP
        )

        val shinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        shinePaint.shader = shineGradient
        shinePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)

        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.save()
        canvas.clipRect(rect)
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, shinePaint)
        canvas.restore()
    }

    fun drawDropShadow(view: View, canvas: Canvas, width: Int, height: Int, cornerRadius: Float) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        shadowPaint.color = YueThemeConfig.primaryColor
        shadowPaint.setShadowLayer(
            YueThemeConfig.shadowRadius,
            0f,
            YueThemeConfig.shadowRadius / 2,
            YueThemeConfig.withAlpha(YueThemeConfig.primaryDarkColor, 0.4f)
        )
        val rect = RectF(8f, 8f, width - 8f, height - 8f)
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, shadowPaint)
    }

    companion object {
        fun createHighQualityBitmap(width: Int, height: Int): Bitmap {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
    }
}
