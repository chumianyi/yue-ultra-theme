package com.yue.ultra.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.theme.LocalYueThemeColors

/**
 * 极致Yue Ultra 透明选择框
 * 透明背景，更加绚丽的光影效果
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> YueSpinner(
    items: List<T>,
    selectedIndex: Int,
    onItemSelected: (Int, T) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    itemFormatter: (T) -> String = { it.toString() },
    cornerRadius: Int = 20,
    expandedByDefault: Boolean = false
) {
    var expanded by remember { mutableStateOf(expandedByDefault) }
    val colors = LocalYueThemeColors.current

    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "arrowRotation"
    )

    val glowIntensity by animateFloatAsState(
        targetValue = if (expanded) 1f else 0.7f,
        animationSpec = tween(durationMillis = 300),
        label = "glowIntensity"
    )

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Box {
            // 选择框主体
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .yueGlowBackground(
                        cornerRadius = cornerRadius.dp,
                        intensity = glowIntensity
                    )
                    .then(
                        if (expanded) {
                            Modifier.yueAnimatedShine(cornerRadius = cornerRadius.dp)
                        } else Modifier
                    )
                    .clip(RoundedCornerShape(cornerRadius.dp))
                    .border(
                        width = if (expanded) 2.dp else 1.dp,
                        color = if (expanded) colors.glowColor else colors.primaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(cornerRadius.dp)
                    )
                    .yueInnerShadow(cornerRadius = cornerRadius.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { expanded = !expanded }
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = if (selectedIndex in items.indices) itemFormatter(items[selectedIndex]) else "请选择",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "展开",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(arrowRotation)
                )
            }

            // 下拉列表
            androidx.compose.animation.AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 300, easing = EaseOutCubic)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 200, easing = EaseInCubic)
                )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .heightIn(max = 240.dp)
                        .yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 0.9f)
                        .clip(RoundedCornerShape(cornerRadius.dp))
                        .border(
                            width = 1.dp,
                            color = colors.glowColor.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(cornerRadius.dp)
                        )
                ) {
                    items(items) { item ->
                        val index = items.indexOf(item)
                        val isSelected = index == selectedIndex

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onItemSelected(index, item)
                                    expanded = false
                                }
                                .background(
                                    if (isSelected) colors.primary.copy(alpha = 0.3f)
                                    else Color.Transparent
                                )
                                .padding(horizontal = 20.dp, vertical = 14.dp)
                        ) {
                            Text(
                                text = itemFormatter(item),
                                color = if (isSelected) colors.glowColor else Color.White,
                                fontSize = 15.sp,
                                modifier = Modifier.weight(1f)
                            )

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "已选中",
                                    tint = colors.glowColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
