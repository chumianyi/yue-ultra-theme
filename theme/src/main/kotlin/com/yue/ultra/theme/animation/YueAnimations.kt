package com.yue.ultra.theme.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yue.ultra.theme.theme.LocalYueThemeColors
import kotlin.math.sin

/**
 * 极致Yue Ultra 动画组件
 */

// 脉冲动画
@Composable
fun YuePulse(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier.scale(scale),
        content = { content() }
    )
}

// 呼吸动画
@Composable
fun YueBreathing(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    minAlpha: Float = 0.5f,
    maxAlpha: Float = 1f,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val alpha by infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = maxAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathingAlpha"
    )

    Box(
        modifier = modifier.graphicsLayer { this.alpha = alpha },
        content = { content() }
    )
}

// 旋转动画
@Composable
fun YueRotating(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotating")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier.rotate(rotation),
        content = { content() }
    )
}

// 弹跳动画
@Composable
fun YueBouncing(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1000,
    height: Float = 20f,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bouncing")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -height,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounceOffset"
    )

    Box(
        modifier = modifier.graphicsLayer { translationY = offsetY },
        content = { content() }
    )
}

// 闪烁动画
@Composable
fun YueBlinking(
    modifier: Modifier = Modifier,
    durationMillis: Int = 500,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "blinking")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkAlpha"
    )

    Box(
        modifier = modifier.graphicsLayer { this.alpha = alpha },
        content = { content() }
    )
}

// 波浪动画
@Composable
fun YueWave(
    modifier: Modifier = Modifier,
    waveCount: Int = 5,
    durationMillis: Int = 1500,
    color: Color? = null
) {
    val colors = LocalYueThemeColors.current
    val waveColor = color ?: colors.glowColor

    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wavePhase"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(waveCount) { index ->
            val waveHeight = (sin(phase + index * 0.5f) * 0.5f + 0.5f) * 24f + 8f

            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(waveHeight.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(waveColor)
            )
        }
    }
}

// 骨架屏加载
@Composable
fun YueSkeleton(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 12
) {
    val colors = LocalYueThemeColors.current
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "skeletonAlpha"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(colors.primary.copy(alpha = alpha))
    )
}

// 骨架屏列表项
@Composable
fun YueSkeletonListItem(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        YueSkeleton(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp)),
            cornerRadius = 24
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            YueSkeleton(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            YueSkeleton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(12.dp)
            )
        }
    }
}

// 淡入淡出动画包装
@Composable
fun YueFadeInOut(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enterDuration: Int = 300,
    exitDuration: Int = 200,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(
            animationSpec = tween(enterDuration, easing = EaseOutCubic)
        ) + expandVertically(
            animationSpec = tween(enterDuration, easing = EaseOutCubic)
        ),
        exit = fadeOut(
            animationSpec = tween(exitDuration, easing = EaseInCubic)
        ) + shrinkVertically(
            animationSpec = tween(exitDuration, easing = EaseInCubic)
        ),
        content = content
    )
}

// 滑动进入动画
@Composable
fun YueSlideIn(
    visible: Boolean,
    modifier: Modifier = Modifier,
    direction: SlideDirection = SlideDirection.Bottom,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    val enterTransition = when (direction) {
        SlideDirection.Bottom -> slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(400, easing = EaseOutCubic)
        )
        SlideDirection.Top -> slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(400, easing = EaseOutCubic)
        )
        SlideDirection.Start -> slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(400, easing = EaseOutCubic)
        )
        SlideDirection.End -> slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(400, easing = EaseOutCubic)
        )
    }

    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enterTransition + fadeIn(),
        exit = fadeOut(),
        content = content
    )
}

enum class SlideDirection {
    Top, Bottom, Start, End
}

// 缩放进入动画
@Composable
fun YueScaleIn(
    visible: Boolean,
    modifier: Modifier = Modifier,
    initialScale: Float = 0.8f,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = scaleIn(
            initialScale = initialScale,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ) + fadeIn(),
        exit = scaleOut(
            targetScale = initialScale,
            animationSpec = tween(200)
        ) + fadeOut(),
        content = content
    )
}
