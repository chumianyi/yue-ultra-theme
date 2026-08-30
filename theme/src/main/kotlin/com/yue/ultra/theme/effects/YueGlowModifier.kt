package com.yue.ultra.theme.effects

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yue.ultra.theme.theme.LocalYueThemeColors
import kotlin.math.sin

/**
 * 极致Yue Ultra 光影效果 Modifier 扩展
 * 2048x级别光影渲染，表面泛光
 */

// 光晕背景
fun Modifier.yueGlowBackground(
    cornerRadius: Dp = 24.dp,
    intensity: Float = 0.8f
): Modifier = composed {
    val colors = LocalYueThemeColors.current
    val glowColor = colors.glowColor
    val primaryColor = colors.primary

    this.drawBehind {
        val width = this.size.width
        val height = this.size.height
        val radius = cornerRadius.toPx()

        // 径向光晕
        val radialGradient = Brush.radialGradient(
            colors = listOf(
                glowColor.copy(alpha = 0.4f * intensity),
                primaryColor.copy(alpha = 0.2f * intensity),
                Color.Transparent
            ),
            center = Offset(width / 2, height / 2),
            radius = kotlin.math.max(width, height)
        )

        drawRoundRect(
            brush = radialGradient,
            cornerRadius = CornerRadius(radius, radius),
            size = Size(width, height)
        )

        // 表面光泽 - 线性渐变
        val shineGradient = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.15f * intensity),
                Color.White.copy(alpha = 0.03f * intensity),
                glowColor.copy(alpha = 0.1f * intensity),
                Color.White.copy(alpha = 0.08f * intensity)
            ),
            start = Offset(0f, 0f),
            end = Offset(width, height)
        )

        drawRoundRect(
            brush = shineGradient,
            cornerRadius = CornerRadius(radius, radius),
            size = Size(width, height),
            blendMode = BlendMode.Screen
        )
    }
}

// 光晕边框
fun Modifier.yueGlowBorder(
    cornerRadius: Dp = 24.dp,
    borderWidth: Dp = 2.dp,
    intensity: Float = 0.8f
): Modifier = composed {
    val colors = LocalYueThemeColors.current
    val glowColor = colors.glowColor
    val primaryLight = colors.primaryContainer

    this.drawBehind {
        val width = this.size.width
        val height = this.size.height
        val radius = cornerRadius.toPx()
        val stroke = borderWidth.toPx()

        // 渐变边框
        val borderGradient = Brush.linearGradient(
            colors = listOf(
                glowColor.copy(alpha = 0.8f * intensity),
                primaryLight.copy(alpha = 0.6f * intensity),
                colors.primary.copy(alpha = 0.7f * intensity),
                glowColor.copy(alpha = 0.8f * intensity)
            ),
            start = Offset(0f, 0f),
            end = Offset(width, 0f)
        )

        drawRoundRect(
            brush = borderGradient,
            cornerRadius = CornerRadius(radius, radius),
            style = Stroke(width = stroke),
            size = Size(width - stroke, height - stroke),
            topLeft = Offset(stroke / 2, stroke / 2)
        )
    }
}

// 动画光泽扫过效果
fun Modifier.yueAnimatedShine(
    cornerRadius: Dp = 24.dp,
    durationMillis: Int = 3000,
    intensity: Float = 0.6f
): Modifier = composed {
    val colors = LocalYueThemeColors.current
    val glowColor = colors.glowColor

    val infiniteTransition = rememberInfiniteTransition(label = "shine")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shineProgress"
    )

    this.drawWithContent {
        drawContent()

        val width = this.size.width
        val height = this.size.height
        val radius = cornerRadius.toPx()
        val shineWidth = width * 0.3f
        val shineX = -shineWidth + progress * (width + shineWidth * 2)

        clipRect {
            val shineGradient = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.White.copy(alpha = 0.4f * intensity),
                    glowColor.copy(alpha = 0.3f * intensity),
                    Color.Transparent
                ),
                start = Offset(shineX, 0f),
                end = Offset(shineX + shineWidth, height)
            )

            drawRoundRect(
                brush = shineGradient,
                cornerRadius = CornerRadius(radius, radius),
                size = Size(width, height),
                blendMode = BlendMode.Screen
            )
        }
    }
}

// 呼吸光晕效果
fun Modifier.yueBreathingGlow(
    cornerRadius: Dp = 24.dp,
    durationMillis: Int = 2000,
    minIntensity: Float = 0.3f,
    maxIntensity: Float = 0.8f
): Modifier = composed {
    val colors = LocalYueThemeColors.current
    val glowColor = colors.glowColor

    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val intensity by infiniteTransition.animateFloat(
        initialValue = minIntensity,
        targetValue = maxIntensity,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathingIntensity"
    )

    this.drawBehind {
        val width = this.size.width
        val height = this.size.height
        val radius = cornerRadius.toPx()

        val radialGradient = Brush.radialGradient(
            colors = listOf(
                glowColor.copy(alpha = 0.5f * intensity),
                glowColor.copy(alpha = 0.2f * intensity),
                Color.Transparent
            ),
            center = Offset(width / 2, height / 2),
            radius = kotlin.math.max(width, height) * 0.8f
        )

        drawRoundRect(
            brush = radialGradient,
            cornerRadius = CornerRadius(radius, radius),
            size = Size(width, height)
        )
    }
}

// 波纹效果
fun Modifier.yueRippleEffect(
    cornerRadius: Dp = 24.dp
): Modifier = composed {
    val colors = LocalYueThemeColors.current

    this.then(
        Modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colors.primary.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            )
    )
}

// 内部阴影
fun Modifier.yueInnerShadow(
    cornerRadius: Dp = 24.dp,
    shadowColor: Color = Color.Black,
    shadowAlpha: Float = 0.15f,
    blurRadius: Dp = 8.dp
): Modifier = composed {
    this.drawBehind {
        val width = this.size.width
        val height = this.size.height
        val radius = cornerRadius.toPx()

        // 顶部内部阴影
        val topShadow = Brush.verticalGradient(
            colors = listOf(
                shadowColor.copy(alpha = shadowAlpha),
                Color.Transparent
            ),
            startY = 0f,
            endY = blurRadius.toPx() * 2
        )

        drawRoundRect(
            brush = topShadow,
            cornerRadius = CornerRadius(radius, radius),
            size = Size(width, height)
        )
    }
}

// 霓虹发光效果
fun Modifier.yueNeonGlow(
    cornerRadius: Dp = 24.dp,
    glowRadius: Dp = 16.dp,
    intensity: Float = 0.8f
): Modifier = composed {
    val colors = LocalYueThemeColors.current
    val glowColor = colors.glowColor

    this.drawBehind {
        val width = this.size.width
        val height = this.size.height
        val radius = cornerRadius.toPx()

        // 多层发光
        for (i in 1..3) {
            val layerIntensity = intensity / i
            val layerRadius = glowRadius.toPx() * i

            drawRoundRect(
                color = glowColor.copy(alpha = 0.1f * layerIntensity),
                cornerRadius = CornerRadius(radius + layerRadius, radius + layerRadius),
                size = Size(width + layerRadius * 2, height + layerRadius * 2),
                topLeft = Offset(-layerRadius, -layerRadius)
            )
        }
    }
}

// 玻璃拟态效果
fun Modifier.yueGlassMorphism(
    cornerRadius: Dp = 24.dp,
    blur: Float = 20f,
    transparency: Float = 0.2f
): Modifier = composed {
    val colors = LocalYueThemeColors.current

    this
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = transparency),
                    Color.White.copy(alpha = transparency * 0.5f)
                )
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
        .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.5f),
                    Color.White.copy(alpha = 0.1f)
                )
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
        .yueGlowBackground(cornerRadius = cornerRadius, intensity = 0.3f)
}

// 完整的YueUltra效果组合
fun Modifier.yueUltraEffect(
    cornerRadius: Dp = 24.dp,
    animatedShine: Boolean = true,
    neonGlow: Boolean = false
): Modifier = composed {
    var modifier = this
        .yueGlowBackground(cornerRadius = cornerRadius)
        .yueGlowBorder(cornerRadius = cornerRadius)
        .yueInnerShadow(cornerRadius = cornerRadius)

    if (animatedShine) {
        modifier = modifier.yueAnimatedShine(cornerRadius = cornerRadius)
    }

    if (neonGlow) {
        modifier = modifier.yueNeonGlow(cornerRadius = cornerRadius)
    }

    modifier
}
