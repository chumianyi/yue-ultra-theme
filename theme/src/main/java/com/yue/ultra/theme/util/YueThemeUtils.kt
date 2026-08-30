package com.yue.ultra.theme.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import com.yue.ultra.theme.YueThemeConfig

/**
 * 极致Yue Ultra 主题工具类
 */
object YueThemeUtils {

    /**
     * 应用沉浸式状态栏
     */
    fun applyImmersiveStatusBar(activity: Activity) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /**
     * 应用透明导航栏
     */
    fun applyTransparentNavigationBar(activity: Activity) {
        activity.window.apply {
            navigationBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }

    /**
     * 应用全屏主题
     */
    fun applyFullscreenTheme(activity: Activity) {
        applyImmersiveStatusBar(activity)
        applyTransparentNavigationBar(activity)
    }

    /**
     * 应用紫色主题
     */
    fun applyPurpleTheme() {
        YueThemeConfig.applyTheme(YueThemeConfig.PURPLE_THEME)
    }

    /**
     * 应用绿色主题
     */
    fun applyGreenTheme() {
        YueThemeConfig.applyTheme(YueThemeConfig.GREEN_THEME)
    }

    /**
     * 应用蓝色主题
     */
    fun applyBlueTheme() {
        YueThemeConfig.applyTheme(YueThemeConfig.BLUE_THEME)
    }

    /**
     * 应用金色主题
     */
    fun applyGoldTheme() {
        YueThemeConfig.applyTheme(YueThemeConfig.GOLD_THEME)
    }

    /**
     * 应用红色主题
     */
    fun applyRedTheme() {
        YueThemeConfig.applyTheme(YueThemeConfig.RED_THEME)
    }

    /**
     * 应用自定义颜色主题
     */
    fun applyCustomTheme(color: Int) {
        YueThemeConfig.applyCustomColor(color)
    }

    /**
     * 设置光影强度
     */
    fun setGlowIntensity(intensity: Float) {
        YueThemeConfig.glowIntensity = intensity.coerceIn(0f, 1f)
    }

    /**
     * 设置透明度
     */
    fun setTransparencyLevel(level: Float) {
        YueThemeConfig.transparencyLevel = level.coerceIn(0f, 1f)
    }

    /**
     * 设置阴影半径
     */
    fun setShadowRadius(radius: Float) {
        YueThemeConfig.shadowRadius = radius.coerceIn(0f, 48f)
    }

    /**
     * 设置动画时长
     */
    fun setAnimationDuration(duration: Long) {
        YueThemeConfig.animationDuration = duration.coerceIn(100L, 1000L)
    }

    /**
     * 设置滑动灵敏度
     */
    fun setSwipeSensitivity(sensitivity: Float) {
        YueThemeConfig.swipeSensitivity = sensitivity.coerceIn(0.5f, 2f)
    }

    /**
     * 获取主题信息
     */
    fun getThemeInfo(): ThemeInfo {
        return ThemeInfo(
            primaryColor = YueThemeConfig.primaryColor,
            glowColor = YueThemeConfig.glowColor,
            glowIntensity = YueThemeConfig.glowIntensity,
            transparencyLevel = YueThemeConfig.transparencyLevel,
            shadowRadius = YueThemeConfig.shadowRadius,
            animationDuration = YueThemeConfig.animationDuration
        )
    }

    data class ThemeInfo(
        val primaryColor: Int,
        val glowColor: Int,
        val glowIntensity: Float,
        val transparencyLevel: Float,
        val shadowRadius: Float,
        val animationDuration: Long
    )
}
