package com.yue.ultra.theme.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.theme.LocalYueThemeColors

/**
 * 极致Yue Ultra 高级组件
 * 对话框、底部弹窗、列表项等
 */

// 对话框
@Composable
fun YueAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String = "确定",
    dismissButtonText: String = "取消",
    onConfirm: (() -> Unit)? = null,
    icon: ImageVector? = null
) {
    if (show) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            YueCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                cornerRadius = 28,
                animatedShine = true
            ) {
                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = LocalYueThemeColors.current.glowColor,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                if (title != null) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = if (text != null) 12.dp else 24.dp)
                    )
                }

                if (text != null) {
                    Text(
                        text = text,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    YueOutlinedButton(
                        text = dismissButtonText,
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        cornerRadius = 16
                    )

                    if (onConfirm != null) {
                        YueButton(
                            text = confirmButtonText,
                            onClick = {
                                onConfirm()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            cornerRadius = 16
                        )
                    }
                }
            }
        }
    }
}

// 底部弹窗
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YueBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.Transparent,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.3f))
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .yueGlowBackground(cornerRadius = 32.dp, intensity = 0.8f)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                content()
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// 列表项
@Composable
fun YueListItem(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    icon: ImageVector? = null,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    showDivider: Boolean = true
) {
    val colors = LocalYueThemeColors.current

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .then(
                    if (onClick != null) Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClick() }
                    else Modifier
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            if (icon != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.primary.copy(alpha = 0.3f))
                        .border(
                            width = 1.dp,
                            color = colors.primaryContainer.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = colors.glowColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            if (trailing != null) {
                Spacer(modifier = Modifier.width(12.dp))
                trailing()
            }
        }

        if (showDivider) {
            HorizontalDivider(
                color = Color.White.copy(alpha = 0.1f),
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// 分隔线
@Composable
fun YueDivider(
    modifier: Modifier = Modifier,
    thickness: Int = 1
) {
    HorizontalDivider(
        modifier = modifier,
        color = Color.White.copy(alpha = 0.15f),
        thickness = thickness.dp
    )
}

// 徽章
@Composable
fun YueBadge(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = 99
) {
    val colors = LocalYueThemeColors.current
    val displayText = if (count > maxCount) "$maxCount+" else count.toString()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .defaultMinSize(minWidth = 20.dp, minHeight = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colors.error)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = displayText,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}

// 头像
@Composable
fun YueAvatar(
    text: String,
    modifier: Modifier = Modifier,
    size: Int = 48,
    backgroundColor: Color? = null
) {
    val colors = LocalYueThemeColors.current
    val bgColor = backgroundColor ?: colors.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(size / 2))
            .background(bgColor)
            .border(
                width = 2.dp,
                color = colors.glowColor.copy(alpha = 0.6f),
                shape = RoundedCornerShape(size / 2)
            )
    ) {
        Text(
            text = text.take(1).uppercase(),
            color = Color.White,
            fontSize = (size / 2.5).sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}

// 空状态
@Composable
fun YueEmptyState(
    icon: ImageVector = Icons.Default.Inbox,
    title: String = "暂无数据",
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    val colors = LocalYueThemeColors.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(colors.primary.copy(alpha = 0.2f))
                .border(
                    width = 1.dp,
                    color = colors.primaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(40.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = colors.glowColor.copy(alpha = 0.8f),
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )

        if (subtitle != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// 加载状态
@Composable
fun YueLoadingState(
    text: String = "加载中...",
    modifier: Modifier = Modifier
) {
    val colors = LocalYueThemeColors.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        CircularProgressIndicator(
            color = colors.glowColor,
            trackColor = colors.primary.copy(alpha = 0.2f),
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 15.sp
        )
    }
}
