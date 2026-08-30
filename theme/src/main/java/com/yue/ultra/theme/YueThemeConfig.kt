package com.yue.ultra.theme

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * 极致Yue Ultra 主题配置
 * 默认紫色，可调整为绿色等其他颜色
 */
object YueThemeConfig {

    // 默认紫色主题
    @ColorInt
    var primaryColor: Int = Color.parseColor("#9C27B0")

    @ColorInt
    var primaryDarkColor: Int = Color.parseColor("#7B1FA2")

    @ColorInt
    var primaryLightColor: Int = Color.parseColor("#CE93D8")

    @ColorInt
    var accentColor: Int = Color.parseColor("#E1BEE7")

    @ColorInt
    var glowColor: Int = Color.parseColor("#E040FB")

    // 透明度配置
    var transparencyLevel: Float = 0.3f
    var glowIntensity: Float = 0.8f
    var shadowRadius: Float = 24f

    // 动画配置
    var animationDuration: Long = 300
    var swipeSensitivity: Float = 1.0f

    // 预设主题
    val PURPLE_THEME = ThemeColors(
        primary = Color.parseColor("#9C27B0"),
        primaryDark = Color.parseColor("#7B1FA2"),
        primaryLight = Color.parseColor("#CE93D8"),
        accent = Color.parseColor("#E1BEE7"),
        glow = Color.parseColor("#E040FB")
    )

    val GREEN_THEME = ThemeColors(
        primary = Color.parseColor("#4CAF50"),
        primaryDark = Color.parseColor("#388E3C"),
        primaryLight = Color.parseColor("#A5D6A7"),
        accent = Color.parseColor("#C8E6C9"),
        glow = Color.parseColor("#69F0AE")
    )

    val BLUE_THEME = ThemeColors(
        primary = Color.parseColor("#2196F3"),
        primaryDark = Color.parseColor("#1976D2"),
        primaryLight = Color.parseColor("#90CAF9"),
        accent = Color.parseColor("#BBDEFB"),
        glow = Color.parseColor("#40C4FF")
    )

    val GOLD_THEME = ThemeColors(
        primary = Color.parseColor("#FFC107"),
        primaryDark = Color.parseColor("#FFA000"),
        primaryLight = Color.parseColor("#FFE082"),
        accent = Color.parseColor("#FFECB3"),
        glow = Color.parseColor("#FFD740")
    )

    val RED_THEME = ThemeColors(
        primary = Color.parseColor("#F44336"),
        primaryDark = Color.parseColor("#D32F2F"),
        primaryLight = Color.parseColor("#EF9A9A"),
        accent = Color.parseColor("#FFCDD2"),
        glow = Color.parseColor("#FF5252")
    )

    fun applyTheme(theme: ThemeColors) {
        primaryColor = theme.primary
        primaryDarkColor = theme.primaryDark
        primaryLightColor = theme.primaryLight
        accentColor = theme.accent
        glowColor = theme.glow
    }

    fun applyCustomColor(@ColorInt color: Int) {
        primaryColor = color
        primaryDarkColor = darkenColor(color, 0.2f)
        primaryLightColor = lightenColor(color, 0.3f)
        accentColor = lightenColor(color, 0.5f)
        glowColor = lightenColor(color, 0.15f)
    }

    fun darkenColor(@ColorInt color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * (1 - factor)).toInt()
        val g = (Color.green(color) * (1 - factor)).toInt()
        val b = (Color.blue(color) * (1 - factor)).toInt()
        return Color.argb(a, r.coerceIn(0, 255), g.coerceIn(0, 255), b.coerceIn(0, 255))
    }

    fun lightenColor(@ColorInt color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) + (255 - Color.red(color)) * factor).toInt()
        val g = (Color.green(color) + (255 - Color.green(color)) * factor).toInt()
        val b = (Color.blue(color) + (255 - Color.blue(color)) * factor).toInt()
        return Color.argb(a, r.coerceIn(0, 255), g.coerceIn(0, 255), b.coerceIn(0, 255))
    }

    fun withAlpha(@ColorInt color: Int, alpha: Float): Int {
        return Color.argb((alpha * 255).toInt(), Color.red(color), Color.green(color), Color.blue(color))
    }

    data class ThemeColors(
        @ColorInt val primary: Int,
        @ColorInt val primaryDark: Int,
        @ColorInt val primaryLight: Int,
        @ColorInt val accent: Int,
        @ColorInt val glow: Int
    )
}
