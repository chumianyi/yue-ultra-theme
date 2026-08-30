package com.yue.ultra.theme.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// 极致Yue Ultra 主题色板
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// 极致Yue Ultra 专属颜色
val YueGlowPurple = Color(0xFFBB86FC)
val YueGlowPink = Color(0xFFFF80AB)
val YueGlowBlue = Color(0xFF81D4FA)
val YueGlowGreen = Color(0xFF69F0AE)
val YueGlowGold = Color(0xFFFFD740)
val YueGlowRed = Color(0xFFFF5252)

// 主题类型枚举
enum class YueThemeType {
    PURPLE, GREEN, BLUE, GOLD, RED, CUSTOM
}

// 主题配置数据类
data class YueThemeColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceTint: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,
    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,
    val glowColor: Color,
    val glowIntensity: Float
)

// 紫色主题
val PurpleYueColors = YueThemeColors(
    primary = Color(0xFF9C27B0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCE93D8),
    onPrimaryContainer = Color(0xFF4A148C),
    secondary = Color(0xFFE040FB),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE1BEE7),
    onSecondaryContainer = Color(0xFF4A148C),
    tertiary = Color(0xFF7C4DFF),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFB388FF),
    onTertiaryContainer = Color(0xFF311B92),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFF1A1A2E),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1A1A2E),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFFD0BCFF),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF6750A4),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    scrim = Color.Black,
    glowColor = YueGlowPurple,
    glowIntensity = 0.8f
)

// 绿色主题
val GreenYueColors = YueThemeColors(
    primary = Color(0xFF4CAF50),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA5D6A7),
    onPrimaryContainer = Color(0xFF1B5E20),
    secondary = Color(0xFF69F0AE),
    onSecondary = Color(0xFF1B5E20),
    secondaryContainer = Color(0xFFC8E6C9),
    onSecondaryContainer = Color(0xFF1B5E20),
    tertiary = Color(0xFF00E676),
    onTertiary = Color(0xFF1B5E20),
    tertiaryContainer = Color(0xFFB9F6CA),
    onTertiaryContainer = Color(0xFF1B5E20),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFF0D1F12),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF0D1F12),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFFA5D6A7),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF4CAF50),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    scrim = Color.Black,
    glowColor = YueGlowGreen,
    glowIntensity = 0.8f
)

// 蓝色主题
val BlueYueColors = YueThemeColors(
    primary = Color(0xFF2196F3),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF90CAF9),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF40C4FF),
    onSecondary = Color(0xFF0D47A1),
    secondaryContainer = Color(0xFFBBDEFB),
    onSecondaryContainer = Color(0xFF0D47A1),
    tertiary = Color(0xFF00B0FF),
    onTertiary = Color(0xFF0D47A1),
    tertiaryContainer = Color(0xFF80D8FF),
    onTertiaryContainer = Color(0xFF0D47A1),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFF0D1520),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF0D1520),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFF90CAF9),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF2196F3),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    scrim = Color.Black,
    glowColor = YueGlowBlue,
    glowIntensity = 0.8f
)

// 金色主题
val GoldYueColors = YueThemeColors(
    primary = Color(0xFFFFC107),
    onPrimary = Color(0xFF3E2723),
    primaryContainer = Color(0xFFFFE082),
    onPrimaryContainer = Color(0xFFE65100),
    secondary = Color(0xFFFFD740),
    onSecondary = Color(0xFF3E2723),
    secondaryContainer = Color(0xFFFFECB3),
    onSecondaryContainer = Color(0xFFE65100),
    tertiary = Color(0xFFFFAB00),
    onTertiary = Color(0xFF3E2723),
    tertiaryContainer = Color(0xFFFFD54F),
    onTertiaryContainer = Color(0xFFE65100),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFF1F1A0D),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1F1A0D),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFFFFE082),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFFFFC107),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    scrim = Color.Black,
    glowColor = YueGlowGold,
    glowIntensity = 0.8f
)

// 红色主题
val RedYueColors = YueThemeColors(
    primary = Color(0xFFF44336),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEF9A9A),
    onPrimaryContainer = Color(0xFFB71C1C),
    secondary = Color(0xFFFF5252),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFCDD2),
    onSecondaryContainer = Color(0xFFB71C1C),
    tertiary = Color(0xFFFF1744),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFF8A80),
    onTertiaryContainer = Color(0xFFB71C1C),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFF1F0D0D),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1F0D0D),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFFEF9A9A),
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFFF44336),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    scrim = Color.Black,
    glowColor = YueGlowRed,
    glowIntensity = 0.8f
)

// CompositionLocal
val LocalYueThemeColors = staticCompositionLocalOf { PurpleYueColors }
val LocalYueThemeType = staticCompositionLocalOf { YueThemeType.PURPLE }

// 获取主题颜色
fun getYueColors(type: YueThemeType): YueThemeColors = when (type) {
    YueThemeType.PURPLE -> PurpleYueColors
    YueThemeType.GREEN -> GreenYueColors
    YueThemeType.BLUE -> BlueYueColors
    YueThemeType.GOLD -> GoldYueColors
    YueThemeType.RED -> RedYueColors
    YueThemeType.CUSTOM -> PurpleYueColors
}

// 转换为Material3 ColorScheme
fun YueThemeColors.toColorScheme(darkTheme: Boolean = true) = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = onSecondaryContainer,
    tertiary = tertiary,
    onTertiary = onTertiary,
    tertiaryContainer = tertiaryContainer,
    onTertiaryContainer = onTertiaryContainer,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    surfaceTint = surfaceTint,
    inverseSurface = inverseSurface,
    inverseOnSurface = inverseOnSurface,
    inversePrimary = inversePrimary,
    outline = outline,
    outlineVariant = outlineVariant,
    scrim = scrim
)

// 极致Yue Ultra 主题
@Composable
fun YueUltraTheme(
    themeType: YueThemeType = YueThemeType.PURPLE,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getYueColors(themeType)
    val colorScheme = colors.toColorScheme(darkTheme)

    CompositionLocalProvider(
        LocalYueThemeColors provides colors,
        LocalYueThemeType provides themeType
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = YueTypography,
            shapes = YueShapes,
            content = content
        )
    }
}
