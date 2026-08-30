package com.yue.ultra.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yue.ultra.theme.animation.*
import com.yue.ultra.theme.components.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.extensions.*
import com.yue.ultra.theme.theme.*
import com.yue.ultra.theme.utils.YueThemeInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YueUltraTheme(
                themeType = YueThemeType.PURPLE,
                darkTheme = true
            ) {
                DemoScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var currentTheme by remember { mutableStateOf(YueThemeType.PURPLE) }

    val tabs = listOf("组件", "动画", "主题", "关于")

    Scaffold(
        containerColor = Color(0xFF0D0D1A),
        topBar = {
            YueTopAppBar(
                title = "极致Yue Ultra v2.0",
                actions = {
                    YueIconButton(
                        icon = Icons.Default.Palette,
                        onClick = {
                            currentTheme = when (currentTheme) {
                                YueThemeType.PURPLE -> YueThemeType.GREEN
                                YueThemeType.GREEN -> YueThemeType.BLUE
                                YueThemeType.BLUE -> YueThemeType.GOLD
                                YueThemeType.GOLD -> YueThemeType.RED
                                YueThemeType.RED -> YueThemeType.PURPLE
                                YueThemeType.CUSTOM -> YueThemeType.PURPLE
                            }
                        }
                    )
                }
            )
        },
        bottomBar = {
            YueNavigationBar(
                items = DefaultNavItems,
                selectedIndex = selectedTab,
                onItemSelected = { selectedTab = it },
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (selectedTab) {
                0 -> ComponentsDemo()
                1 -> AnimationDemo()
                2 -> ThemeDemo(currentTheme) { currentTheme = it }
                3 -> AboutDemo()
            }
        }
    }
}

@Composable
fun ComponentsDemo() {
    var text by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var switchChecked by remember { mutableStateOf(true) }
    var sliderValue by remember { mutableStateOf(0.5f) }
    var progress by remember { mutableStateOf(0.7f) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "输入框组件",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueEditText(
                value = text,
                onValueChange = { text = it },
                label = "普通输入框",
                placeholder = "请输入内容..."
            )
        }

        item {
            YueSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }

        item {
            YuePasswordField(
                value = password,
                onValueChange = { password = it }
            )
        }

        item {
            Text(
                text = "选择框组件",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueSpinner(
                items = listOf("选项一", "选项二", "选项三", "选项四", "选项五"),
                selectedIndex = selectedIndex,
                onItemSelected = { index, _ -> selectedIndex = index },
                label = "下拉选择"
            )
        }

        item {
            Text(
                text = "按钮组件",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                YueButton(
                    text = "主按钮",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Favorite
                )
                YueOutlinedButton(
                    text = "次要按钮",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Star
                )
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                YueIconButton(icon = Icons.Default.Home, onClick = {})
                YueIconButton(icon = Icons.Default.Search, onClick = {})
                YueIconButton(icon = Icons.Default.Settings, onClick = {})
                YueIconButton(icon = Icons.Default.Person, onClick = {})
                YueIconButton(icon = Icons.Default.Notifications, onClick = {})
            }
        }

        item {
            Text(
                text = "卡片组件",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueCard(animatedShine = true) {
                Text(
                    text = "动画光泽卡片",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "这是一个带有动画光泽效果的卡片，表面泛着光，2048x级别光影渲染。",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }

        item {
            YueCard {
                Text(
                    text = "玻璃拟态卡片",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "透明背景，表面泛光，绚丽到让人无法自拔。",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }

        item {
            Text(
                text = "控制组件",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueCard {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "开关控制",
                        color = Color.White,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                    YueSwitch(
                        checked = switchChecked,
                        onCheckedChange = { switchChecked = it }
                    )
                }
            }
        }

        item {
            YueCard {
                YueSlider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    label = "滑块控制"
                )
            }
        }

        item {
            YueCard {
                Text(
                    text = "进度条",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                YueLinearProgressIndicator(progress = progress)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    YueCircularProgressIndicator(progress = progress, size = 64)
                    Column {
                        Text(
                            text = "圆形进度",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${(progress * 100).toInt()}% 完成",
                            color = LocalYueThemeColors.current.glowColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "列表组件",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueCard {
                Column {
                    YueListItem(
                        title = "设置项一",
                        subtitle = "这是设置项的描述",
                        icon = Icons.Default.Settings,
                        trailing = { YueSwitch(checked = true, onCheckedChange = {}) }
                    )
                    YueListItem(
                        title = "设置项二",
                        subtitle = "点击可以跳转",
                        icon = Icons.Default.Person,
                        onClick = {}
                    )
                    YueListItem(
                        title = "设置项三",
                        icon = Icons.Default.Notifications,
                        trailing = {
                            YueBadge(count = 5)
                        },
                        showDivider = false
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun AnimationDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "动画效果展示",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueCard {
                Text(
                    text = "脉冲动画",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    YuePulse {
                        YueAvatar(text = "A", size = 56)
                    }
                    YuePulse {
                        YueAvatar(text = "B", size = 56, backgroundColor = Color(0xFF4CAF50))
                    }
                    YuePulse {
                        YueAvatar(text = "C", size = 56, backgroundColor = Color(0xFF2196F3))
                    }
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "呼吸动画",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    YueBreathing {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "喜欢",
                            tint = Color(0xFFFF5252),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    YueBreathing {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "收藏",
                            tint = Color(0xFFFFD740),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    YueBreathing {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "通知",
                            tint = Color(0xFF40C4FF),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "旋转动画",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    YueRotating {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "同步",
                            tint = LocalYueThemeColors.current.glowColor,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    YueRotating(durationMillis = 1000) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "刷新",
                            tint = Color(0xFF69F0AE),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    YueRotating(durationMillis = 3000) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "设置",
                            tint = Color(0xFFFFD740),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "弹跳动画",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    YueBouncing {
                        YueAvatar(text = "1", size = 48)
                    }
                    YueBouncing(durationMillis = 800) {
                        YueAvatar(text = "2", size = 48, backgroundColor = Color(0xFF4CAF50))
                    }
                    YueBouncing(durationMillis = 1200) {
                        YueAvatar(text = "3", size = 48, backgroundColor = Color(0xFF2196F3))
                    }
                    YueBouncing(durationMillis = 600) {
                        YueAvatar(text = "4", size = 48, backgroundColor = Color(0xFFFF9800))
                    }
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "波浪动画",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                YueWave(waveCount = 8)
            }
        }

        item {
            YueCard {
                Text(
                    text = "骨架屏加载",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Column {
                    YueSkeletonListItem()
                    YueSkeletonListItem()
                    YueSkeletonListItem()
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "丝滑滑动切换",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                YueHorizontalPager(
                    pageCount = 4,
                    style = YuePagerStyle.GLASS,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) { page ->
                    YueCard(
                        modifier = Modifier.fillMaxSize(),
                        animatedShine = true
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "页面 ${page + 1}",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "玻璃拟态滑动效果",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ThemeDemo(
    currentTheme: YueThemeType,
    onThemeChange: (YueThemeType) -> Unit
) {
    val themes = listOf(
        "紫色主题" to YueThemeType.PURPLE,
        "绿色主题" to YueThemeType.GREEN,
        "蓝色主题" to YueThemeType.BLUE,
        "金色主题" to YueThemeType.GOLD,
        "红色主题" to YueThemeType.RED
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "主题切换",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(themes) { (name, theme) ->
            val isSelected = currentTheme == theme
            YueCard(
                modifier = Modifier.yueClickable { onThemeChange(theme) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                when (theme) {
                                    YueThemeType.PURPLE -> Color(0xFF9C27B0)
                                    YueThemeType.GREEN -> Color(0xFF4CAF50)
                                    YueThemeType.BLUE -> Color(0xFF2196F3)
                                    YueThemeType.GOLD -> Color(0xFFFFC107)
                                    YueThemeType.RED -> Color(0xFFF44336)
                                    YueThemeType.CUSTOM -> Color.Gray
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "已选中",
                            tint = LocalYueThemeColors.current.glowColor
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "主题预览",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            YueCard(animatedShine = true) {
                Text(
                    text = "当前主题效果",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(LocalYueThemeColors.current.primary)
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(LocalYueThemeColors.current.glowColor)
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(LocalYueThemeColors.current.primaryContainer)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                YueButton(
                    text = "应用主题",
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun AboutDemo() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            YueCard(
                modifier = Modifier.fillMaxWidth(),
                animatedShine = true
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .background(LocalYueThemeColors.current.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Y",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = YueThemeInfo.NAME,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "v${YueThemeInfo.VERSION}",
                        color = LocalYueThemeColors.current.glowColor,
                        fontSize = 14.sp
                    )
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "功能特性",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                YueThemeInfo.getFeatures().forEach { feature ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = feature,
                            tint = LocalYueThemeColors.current.glowColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = feature,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        item {
            YueCard {
                Text(
                    text = "技术栈",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                listOf(
                    "Jetpack Compose" to "声明式UI",
                    "Material3" to "深度改造",
                    "Kotlin" to "1.9.22",
                    "AndroidX" to "最新稳定版"
                ).forEach { (name, desc) ->
                    YueListItem(
                        title = name,
                        subtitle = desc,
                        icon = Icons.Default.Code,
                        showDivider = false
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
