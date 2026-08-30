package com.yue.ultra.theme.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yue.ultra.theme.theme.YueThemeType

/**
 * 极致Yue Ultra 工具类
 */

// 主题持久化管理
class YueThemeManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("yue_theme_prefs", Context.MODE_PRIVATE)

    var currentTheme: YueThemeType
        get() {
            val ordinal = prefs.getInt("theme_type", YueThemeType.PURPLE.ordinal)
            return YueThemeType.entries.getOrElse(ordinal) { YueThemeType.PURPLE }
        }
        set(value) {
            prefs.edit().putInt("theme_type", value.ordinal).apply()
        }

    var customColor: Long
        get() = prefs.getLong("custom_color", 0xFF9C27B0)
        set(value) {
            prefs.edit().putLong("custom_color", value).apply()
        }

    var glowIntensity: Float
        get() = prefs.getFloat("glow_intensity", 0.8f)
        set(value) {
            prefs.edit().putFloat("glow_intensity", value.coerceIn(0f, 1f)).apply()
        }

    var transparencyLevel: Float
        get() = prefs.getFloat("transparency_level", 0.3f)
        set(value) {
            prefs.edit().putFloat("transparency_level", value.coerceIn(0f, 1f)).apply()
        }

    var animationEnabled: Boolean
        get() = prefs.getBoolean("animation_enabled", true)
        set(value) {
            prefs.edit().putBoolean("animation_enabled", value).apply()
        }

    var darkMode: Boolean
        get() = prefs.getBoolean("dark_mode", true)
        set(value) {
            prefs.edit().putBoolean("dark_mode", value).apply()
        }

    fun reset() {
        prefs.edit().clear().apply()
    }
}

// 颜色工具
object YueColorUtils {
    fun Color.toHexString(): String {
        val alpha = (this.alpha * 255).toInt()
        val red = (this.red * 255).toInt()
        val green = (this.green * 255).toInt()
        val blue = (this.blue * 255).toInt()
        return String.format("#%02X%02X%02X%02X", alpha, red, green, blue)
    }

    fun Color.darken(factor: Float): Color {
        return Color(
            red = (this.red * (1 - factor)).coerceIn(0f, 1f),
            green = (this.green * (1 - factor)).coerceIn(0f, 1f),
            blue = (this.blue * (1 - factor)).coerceIn(0f, 1f),
            alpha = this.alpha
        )
    }

    fun Color.lighten(factor: Float): Color {
        return Color(
            red = (this.red + (1 - this.red) * factor).coerceIn(0f, 1f),
            green = (this.green + (1 - this.green) * factor).coerceIn(0f, 1f),
            blue = (this.blue + (1 - this.blue) * factor).coerceIn(0f, 1f),
            alpha = this.alpha
        )
    }

    fun Color.withAlpha(alpha: Float): Color {
        return this.copy(alpha = alpha.coerceIn(0f, 1f))
    }

    fun mixColors(color1: Color, color2: Color, ratio: Float = 0.5f): Color {
        val r = ratio.coerceIn(0f, 1f)
        return Color(
            red = color1.red * (1 - r) + color2.red * r,
            green = color1.green * (1 - r) + color2.green * r,
            blue = color1.blue * (1 - r) + color2.blue * r,
            alpha = color1.alpha * (1 - r) + color2.alpha * r
        )
    }

    fun isLightColor(color: Color): Boolean {
        val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
        return luminance > 0.5
    }
}

// 尺寸工具
object YueSizeUtils {
    fun Dp.toPx(context: Context): Float {
        return this.value * context.resources.displayMetrics.density
    }

    fun Float.pxToDp(context: Context): Dp {
        return (this / context.resources.displayMetrics.density).dp
    }

    fun Int.pxToDp(context: Context): Dp {
        return (this / context.resources.displayMetrics.density).dp
    }

    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}

// 动画工具
object YueAnimationUtils {
    const val DURATION_SHORT = 150
    const val DURATION_MEDIUM = 300
    const val DURATION_LONG = 500
    const val DURATION_XLONG = 800

    const val EASE_STANDARD = 0.4f
    const val EASE_DECELERATE = 0.0f
    const val EASE_ACCELERATE = 0.4f

    fun lerp(start: Float, stop: Float, fraction: Float): Float {
        return start + (stop - start) * fraction
    }

    fun clamp(value: Float, min: Float, max: Float): Float {
        return value.coerceIn(min, max)
    }

    fun smoothStep(edge0: Float, edge1: Float, x: Float): Float {
        val t = ((x - edge0) / (edge1 - edge0)).coerceIn(0f, 1f)
        return t * t * (3 - 2 * t)
    }
}

// 版本信息
object YueThemeInfo {
    const val NAME = "极致Yue Ultra"
    const val VERSION = "2.0.0"
    const val VERSION_CODE = 200
    const val AUTHOR = "Yue Ultra Team"
    const val DESCRIPTION = "基于Compose Material3深度改造的极致光影主题库"

    fun getFullVersion(): String {
        return "$NAME v$VERSION ($VERSION_CODE)"
    }

    fun getFeatures(): List<String> = listOf(
        "2048x级别光影渲染",
        "表面泛光效果",
        "透明控件（输入框/选择框/导航栏）",
        "丝滑滑动切换（6种动画风格）",
        "5种预设主题（紫/绿/蓝/金/红）",
        "自定义颜色主题",
        "Material3深度改造",
        "Compose声明式UI",
        "呼吸光晕/霓虹发光",
        "玻璃拟态效果",
        "骨架屏加载",
        "丰富的动画组件"
    )
}
