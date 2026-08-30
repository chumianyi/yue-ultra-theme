package com.yue.ultra.theme.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.theme.LocalYueThemeColors

/**
 * 极致Yue Ultra Modifier 扩展函数
 */

// 快速应用YueUltra效果
fun Modifier.yueUltra(
    cornerRadius: Dp = 24.dp,
    animatedShine: Boolean = false,
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

// 快速应用玻璃拟态
fun Modifier.yueGlass(
    cornerRadius: Dp = 24.dp,
    transparency: Float = 0.2f
): Modifier = composed {
    this.yueGlassMorphism(
        cornerRadius = cornerRadius,
        transparency = transparency
    )
}

// 快速应用卡片样式
fun Modifier.yueCard(
    cornerRadius: Dp = 24.dp,
    padding: Dp = 20.dp,
    animatedShine: Boolean = false
): Modifier = composed {
    var modifier = this
        .yueGlowBackground(cornerRadius = cornerRadius, intensity = 0.6f)
        .clip(RoundedCornerShape(cornerRadius))
        .border(
            width = 1.dp,
            color = LocalYueThemeColors.current.primaryContainer.copy(alpha = 0.4f),
            shape = RoundedCornerShape(cornerRadius)
        )
        .yueInnerShadow(cornerRadius = cornerRadius)
        .padding(padding)

    if (animatedShine) {
        modifier = modifier.yueAnimatedShine(cornerRadius = cornerRadius)
    }

    modifier
}

// 无涟漪点击
fun Modifier.yueClickable(
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

// 呼吸效果
fun Modifier.yueBreathing(
    cornerRadius: Dp = 24.dp,
    durationMillis: Int = 2000,
    minIntensity: Float = 0.3f,
    maxIntensity: Float = 0.8f
): Modifier = composed {
    this.yueBreathingGlow(
        cornerRadius = cornerRadius,
        durationMillis = durationMillis,
        minIntensity = minIntensity,
        maxIntensity = maxIntensity
    )
}

// 霓虹效果
fun Modifier.yueNeon(
    cornerRadius: Dp = 24.dp,
    glowRadius: Dp = 16.dp,
    intensity: Float = 0.8f
): Modifier = composed {
    this.yueNeonGlow(
        cornerRadius = cornerRadius,
        glowRadius = glowRadius,
        intensity = intensity
    )
}

// 动画光泽
fun Modifier.yueShine(
    cornerRadius: Dp = 24.dp,
    durationMillis: Int = 3000,
    intensity: Float = 0.6f
): Modifier = composed {
    this.yueAnimatedShine(
        cornerRadius = cornerRadius,
        durationMillis = durationMillis,
        intensity = intensity
    )
}

// 内部阴影
fun Modifier.yueShadow(
    cornerRadius: Dp = 24.dp,
    shadowColor: Color = Color.Black,
    shadowAlpha: Float = 0.15f,
    blurRadius: Dp = 8.dp
): Modifier = composed {
    this.yueInnerShadow(
        cornerRadius = cornerRadius,
        shadowColor = shadowColor,
        shadowAlpha = shadowAlpha,
        blurRadius = blurRadius
    )
}

// 光晕背景
fun Modifier.yueBackground(
    cornerRadius: Dp = 24.dp,
    intensity: Float = 0.8f
): Modifier = composed {
    this.yueGlowBackground(
        cornerRadius = cornerRadius,
        intensity = intensity
    )
}

// 光晕边框
fun Modifier.yueBorder(
    cornerRadius: Dp = 24.dp,
    borderWidth: Dp = 2.dp,
    intensity: Float = 0.8f
): Modifier = composed {
    this.yueGlowBorder(
        cornerRadius = cornerRadius,
        borderWidth = borderWidth,
        intensity = intensity
    )
}
