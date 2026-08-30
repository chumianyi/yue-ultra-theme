package com.yue.ultra.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.theme.LocalYueThemeColors

/**
 * 极致Yue Ultra 透明导航栏
 * 透明背景，表面泛光
 */
data class YueNavItem(
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
)

@Composable
fun YueNavigationBar(
    items: List<YueNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Int = 28,
    showLabels: Boolean = true
) {
    val colors = LocalYueThemeColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 0.7f)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .border(
                width = 1.dp,
                color = colors.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .yueInnerShadow(cornerRadius = cornerRadius.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex

            val itemGlowIntensity by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0f,
                animationSpec = tween(durationMillis = 300),
                label = "itemGlow"
            )

            val iconColor by animateColorAsState(
                targetValue = if (isSelected) colors.glowColor else Color.White.copy(alpha = 0.6f),
                animationSpec = tween(durationMillis = 300),
                label = "iconColor"
            )

            val textColor by animateColorAsState(
                targetValue = if (isSelected) colors.glowColor else Color.White.copy(alpha = 0.6f),
                animationSpec = tween(durationMillis = 300),
                label = "textColor"
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .then(
                        if (isSelected) {
                            Modifier
                                .background(colors.primary.copy(alpha = 0.25f))
                                .yueGlowBorder(cornerRadius = 20.dp, intensity = 0.8f)
                        } else Modifier
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onItemSelected(index) }
                    .padding(vertical = if (showLabels) 8.dp else 12.dp, horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = if (isSelected) item.selectedIcon else item.icon,
                    contentDescription = item.title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )

                if (showLabels) {
                    Text(
                        text = item.title,
                        color = textColor,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// 顶部导航栏
@Composable
fun YueTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    cornerRadius: Int = 20
) {
    val colors = LocalYueThemeColors.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .yueGlowBackground(cornerRadius = cornerRadius.dp, intensity = 0.6f)
            .clip(RoundedCornerShape(cornerRadius.dp))
            .border(
                width = 1.dp,
                color = colors.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (navigationIcon != null) {
            Box(modifier = Modifier.padding(end = 12.dp)) {
                navigationIcon()
            }
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = actions
        )
    }
}

// 预设导航项
val DefaultNavItems = listOf(
    YueNavItem("首页", Icons.Default.Home, Icons.Filled.Home),
    YueNavItem("发现", Icons.Default.Explore, Icons.Filled.Explore),
    YueNavItem("消息", Icons.Default.Email, Icons.Filled.Email),
    YueNavItem("我的", Icons.Default.Person, Icons.Filled.Person)
)
