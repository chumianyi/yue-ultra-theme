package com.yue.ultra.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.theme.LocalYueThemeColors

/**
 * 极致Yue Ultra 按钮组件
 */

// 主按钮
@Composable
fun YueButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    cornerRadius: Int = 24,
    showGlow: Boolean = true
) {
    val colors = LocalYueThemeColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )

    val glowIntensity by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 0.8f,
        animationSpec = tween(durationMillis = 200),
        label = "glowIntensity"
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .scale(scale)
            .then(if (showGlow) Modifier.yueNeonGlow(cornerRadius = cornerRadius.dp, intensity = glowIntensity) else Modifier)
            .yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = if (enabled) 1f else 0.3f)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(
                if (enabled) colors.primary.copy(alpha = 0.6f)
                else Color.Gray.copy(alpha = 0.3f)
            )
            .border(
                width = 1.5.dp,
                color = if (enabled) colors.glowColor else Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
    }
}

// 次要按钮（透明）
@Composable
fun YueOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    cornerRadius: Int = 24
) {
    val colors = LocalYueThemeColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .scale(scale)
            .yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 0.4f)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .border(
                width = 1.5.dp,
                color = if (enabled) colors.glowColor.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (enabled) colors.glowColor else Color.Gray,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = text,
            color = if (enabled) Color.White else Color.Gray,
            fontSize = 16.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
    }
}

// 图标按钮
@Composable
fun YueIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.White,
    size: Int = 48,
    cornerRadius: Int = 16,
    showGlow: Boolean = true
) {
    val colors = LocalYueThemeColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "iconScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.dp)
            .scale(scale)
            .then(if (showGlow) Modifier.yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 0.5f) else Modifier)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .border(
                width = 1.dp,
                color = colors.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size((size * 0.5).dp)
        )
    }
}

// 卡片
@Composable
fun YueCard(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 24,
    showBorder: Boolean = true,
    showGlow: Boolean = true,
    animatedShine: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = LocalYueThemeColors.current

    Column(
        modifier = modifier
            .then(if (showGlow) Modifier.yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 0.6f) else Modifier)
            .then(if (animatedShine) Modifier.yueAnimatedShine(cornerRadius = cornerRadius.dp) else Modifier)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .then(
                if (showBorder) Modifier.border(
                    width = 1.dp,
                    color = colors.primaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(cornerRadius.dp)
                ) else Modifier
            )
            .yueInnerShadow(cornerRadius = cornerRadius.dp)
            .padding(20.dp)
    ) {
        content()
    }
}

// 开关
@Composable
fun YueSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val colors = LocalYueThemeColors.current

    val thumbColor by animateColorAsState(
        targetValue = if (checked) colors.glowColor else Color.Gray,
        animationSpec = tween(durationMillis = 200),
        label = "thumbColor"
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) colors.primary.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.3f),
        animationSpec = tween(durationMillis = 200),
        label = "trackColor"
    )

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = thumbColor,
            uncheckedThumbColor = Color.LightGray,
            checkedTrackColor = trackColor,
            uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f),
            checkedBorderColor = colors.glowColor,
            uncheckedBorderColor = Color.Gray.copy(alpha = 0.5f)
        )
    )
}

// 滑块
@Composable
fun YueSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    enabled: Boolean = true,
    label: String? = null
) {
    val colors = LocalYueThemeColors.current

    Column(modifier = modifier) {
        if (label != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = String.format("%.1f", value),
                    color = colors.glowColor,
                    fontSize = 14.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            valueRange = valueRange,
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = colors.glowColor,
                activeTrackColor = colors.primary,
                inactiveTrackColor = colors.primary.copy(alpha = 0.2f),
                activeTickColor = colors.glowColor,
                inactiveTickColor = colors.primary.copy(alpha = 0.4f)
            )
        )
    }
}

// 进度条
@Composable
fun YueLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    cornerRadius: Int = 8,
    showGlow: Boolean = true
) {
    val colors = LocalYueThemeColors.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(colors.primary.copy(alpha = 0.2f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .then(if (showGlow) Modifier.yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 1f) else Modifier)
                .background(colors.primary)
        )
    }
}

// 圆形进度条
@Composable
fun YueCircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Int = 64,
    strokeWidth: Int = 6,
    showText: Boolean = true
) {
    val colors = LocalYueThemeColors.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = colors.glowColor,
            trackColor = colors.primary.copy(alpha = 0.2f),
            strokeWidth = strokeWidth.dp
        )

        if (showText) {
            Text(
                text = "${(progress * 100).toInt()}%",
                color = Color.White,
                fontSize = (size / 5).sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}

// 标签
@Composable
fun YueChip(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    icon: ImageVector? = null,
    cornerRadius: Int = 16
) {
    val colors = LocalYueThemeColors.current

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) colors.primary.copy(alpha = 0.5f) else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "chipBackground"
    )

    val textColor by animateColorAsState(
        targetValue = if (selected) colors.glowColor else Color.White.copy(alpha = 0.8f),
        animationSpec = tween(durationMillis = 200),
        label = "chipTextColor"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = if (selected) 0.8f else 0.3f)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(backgroundColor)
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) colors.glowColor else colors.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = textColor,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 6.dp)
            )
        }
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp
        )
    }
}
