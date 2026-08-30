package com.yue.ultra.theme.anim

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.yue.ultra.theme.YueThemeConfig
import kotlin.math.abs
import kotlin.math.max

/**
 * 极致Yue Ultra 丝滑滑动切换动画
 * 超丝滑的页面切换效果，带光影过渡
 */
class YueSwipeTransformer : ViewPager2.PageTransformer {

    enum class AnimationStyle {
        DEPTH,
        ZOOM,
        STACK,
        CUBE,
        GLASS
    }

    private var style = AnimationStyle.GLASS
    private var minScale = 0.85f
    private var minAlpha = 0.5f
    private var transitionDuration = 300L

    override fun transformPage(page: View, position: Float) {
        when (style) {
            AnimationStyle.DEPTH -> applyDepthEffect(page, position)
            AnimationStyle.ZOOM -> applyZoomEffect(page, position)
            AnimationStyle.STACK -> applyStackEffect(page, position)
            AnimationStyle.CUBE -> applyCubeEffect(page, position)
            AnimationStyle.GLASS -> applyGlassEffect(page, position)
        }
    }

    /**
     * 玻璃拟态效果 - 默认，最丝滑
     */
    private fun applyGlassEffect(page: View, position: Float) {
        val pageWidth = page.width
        val pageHeight = page.height

        when {
            position < -1 -> {
                page.alpha = 0f
            }
            position <= 1 -> {
                // 透明度渐变
                val alphaFactor = maxOf(minAlpha, 1 - abs(position))
                page.alpha = alphaFactor

                // 缩放效果
                val scaleFactor = maxOf(minScale, 1 - abs(position) * 0.15f)
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor

                // 位移效果
                page.translationX = -position * pageWidth * 0.3f

                // 旋转效果（轻微）
                page.rotationY = position * 15f

                // 阴影效果
                page.elevation = (1 - abs(position)) * 24f

                // 光影过渡
                applyGlowTransition(page, position)
            }
            else -> {
                page.alpha = 0f
            }
        }
    }

    /**
     * 深度效果
     */
    private fun applyDepthEffect(page: View, position: Float) {
        val pageWidth = page.width

        when {
            position < -1 -> {
                page.alpha = 0f
            }
            position <= 0 -> {
                page.alpha = 1f
                page.translationX = 0f
                page.scaleX = 1f
                page.scaleY = 1f
            }
            position <= 1 -> {
                page.alpha = 1 - position
                page.translationX = pageWidth * -position
                val scaleFactor = minScale + (1 - minScale) * (1 - abs(position))
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
            }
            else -> {
                page.alpha = 0f
            }
        }
    }

    /**
     * 缩放效果
     */
    private fun applyZoomEffect(page: View, position: Float) {
        when {
            position < -1 -> {
                page.alpha = 0f
            }
            position <= 1 -> {
                val scale = maxOf(minScale, 1 - abs(position))
                page.scaleX = scale
                page.scaleY = scale
                page.alpha = maxOf(minAlpha, 1 - abs(position))
                page.translationX = page.width * -position * 0.5f
            }
            else -> {
                page.alpha = 0f
            }
        }
    }

    /**
     * 堆叠效果
     */
    private fun applyStackEffect(page: View, position: Float) {
        when {
            position < -1 -> {
                page.alpha = 0f
            }
            position <= 0 -> {
                page.alpha = 1f
                page.translationX = 0f
                page.scaleX = 1f
                page.scaleY = 1f
                page.elevation = -position * 10f
            }
            position <= 1 -> {
                page.alpha = 1 - position * 0.5f
                page.translationX = -page.width * position
                page.scaleX = 1 - position * 0.1f
                page.scaleY = 1 - position * 0.1f
                page.elevation = -position * 10f
            }
            else -> {
                page.alpha = 0f
            }
        }
    }

    /**
     * 立方体效果
     */
    private fun applyCubeEffect(page: View, position: Float) {
        when {
            position < -1 -> {
                page.alpha = 0f
            }
            position <= 1 -> {
                page.pivotX = if (position < 0) page.width.toFloat() else 0f
                page.pivotY = page.height * 0.5f
                page.rotationY = position * -45f
                page.alpha = 1 - abs(position) * 0.3f
                page.translationX = -position * page.width * 0.2f
            }
            else -> {
                page.alpha = 0f
            }
        }
    }

    /**
     * 应用光影过渡效果
     */
    private fun applyGlowTransition(page: View, position: Float) {
        val glowIntensity = (1 - abs(position)) * YueThemeConfig.glowIntensity
        page.alpha = page.alpha * (0.7f + glowIntensity * 0.3f)
    }

    fun setAnimationStyle(style: AnimationStyle) {
        this.style = style
    }

    fun setMinScale(scale: Float) {
        minScale = scale
    }

    fun setMinAlpha(alpha: Float) {
        minAlpha = alpha
    }

    fun setTransitionDuration(duration: Long) {
        transitionDuration = duration
    }

    companion object {
        /**
         * 应用到ViewPager2
         */
        fun applyTo(viewPager: ViewPager2, style: AnimationStyle = AnimationStyle.GLASS) {
            val transformer = YueSwipeTransformer()
            transformer.setAnimationStyle(style)
            viewPager.setPageTransformer(transformer)
        }
    }
}
