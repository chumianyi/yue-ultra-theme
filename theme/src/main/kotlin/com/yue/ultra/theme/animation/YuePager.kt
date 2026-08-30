package com.yue.ultra.theme.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.yue.ultra.theme.components.YueCard
import com.yue.ultra.theme.effects.yueGlowBackground
import com.yue.ultra.theme.theme.LocalYueThemeColors
import kotlin.math.absoluteValue

/**
 * 极致Yue Ultra 丝滑滑动切换
 * 超丝滑的页面切换效果，带光影过渡
 */

// 动画风格枚举
enum class YuePagerStyle {
    GLASS,      // 玻璃拟态
    DEPTH,      // 深度
    ZOOM,       // 缩放
    STACK,      // 堆叠
    CUBE,       // 立方体
    PARALLAX    // 视差
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YueHorizontalPager(
    pageCount: Int,
    modifier: Modifier = Modifier,
    style: YuePagerStyle = YuePagerStyle.GLASS,
    userScrollEnabled: Boolean = true,
    pageSpacing: Int = 16,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp),
    pageContent: @Composable (page: Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pageCount })

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSpacing = pageSpacing.dp,
        userScrollEnabled = userScrollEnabled,
        contentPadding = contentPadding,
        pageSize = PageSize.Fill
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                    when (style) {
                        YuePagerStyle.GLASS -> {
                            // 玻璃拟态效果
                            alpha = lerp(1f, 0.5f, pageOffset.coerceIn(0f, 1f))
                            scaleX = lerp(1f, 0.85f, pageOffset.coerceIn(0f, 1f))
                            scaleY = lerp(1f, 0.85f, pageOffset.coerceIn(0f, 1f))
                            translationX = pageOffset * -30f
                            rotationY = pageOffset * 10f
                        }
                        YuePagerStyle.DEPTH -> {
                            // 深度效果
                            alpha = lerp(1f, 0.3f, pageOffset.coerceIn(0f, 1f))
                            translationX = pageOffset * -100f
                            scaleX = lerp(1f, 0.7f, pageOffset.coerceIn(0f, 1f))
                            scaleY = lerp(1f, 0.7f, pageOffset.coerceIn(0f, 1f))
                        }
                        YuePagerStyle.ZOOM -> {
                            // 缩放效果
                            alpha = lerp(1f, 0.4f, pageOffset.coerceIn(0f, 1f))
                            scaleX = lerp(1f, 0.6f, pageOffset.coerceIn(0f, 1f))
                            scaleY = lerp(1f, 0.6f, pageOffset.coerceIn(0f, 1f))
                        }
                        YuePagerStyle.STACK -> {
                            // 堆叠效果
                            if (page < pagerState.currentPage) {
                                translationX = pageOffset * -50f
                                scaleX = lerp(1f, 0.9f, pageOffset.coerceIn(0f, 1f))
                                scaleY = lerp(1f, 0.9f, pageOffset.coerceIn(0f, 1f))
                                alpha = lerp(1f, 0.5f, pageOffset.coerceIn(0f, 1f))
                            } else {
                                translationX = pageOffset * 0f
                            }
                        }
                        YuePagerStyle.CUBE -> {
                            // 立方体效果
                            rotationY = pageOffset * -30f
                            alpha = lerp(1f, 0.4f, pageOffset.coerceIn(0f, 1f))
                            translationX = pageOffset * -20f
                        }
                        YuePagerStyle.PARALLAX -> {
                            // 视差效果
                            translationX = pageOffset * 50f
                            alpha = lerp(1f, 0.6f, pageOffset.coerceIn(0f, 1f))
                        }
                    }
                }
        ) {
            pageContent(page)
        }
    }
}

// 页面指示器
@Composable
fun YuePagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    onPageClick: ((Int) -> Unit)? = null
) {
    val colors = LocalYueThemeColors.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage

            val width by animateFloatAsState(
                targetValue = if (isSelected) 24f else 8f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "indicatorWidth"
            )

            val color by animateColorAsState(
                targetValue = if (isSelected) colors.glowColor else Color.White.copy(alpha = 0.3f),
                animationSpec = tween(durationMillis = 300),
                label = "indicatorColor"
            )

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
                    .then(
                        if (onPageClick != null) Modifier.pointerInput(index) {
                            detectHorizontalDragGestures { _, _ -> }
                        }.pointerInput(index) {
                            // 点击处理
                        } else Modifier
                    )
            )
        }
    }
}

// 轮播图组件
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YueCarousel(
    items: List<CarouselItem>,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    autoPlayInterval: Long = 3000,
    style: YuePagerStyle = YuePagerStyle.GLASS
) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    var currentPage by remember { mutableIntStateOf(0) }

    // 自动播放
    LaunchedEffect(autoPlay, pagerState.currentPage) {
        if (autoPlay) {
            while (true) {
                delay(autoPlayInterval)
                val nextPage = (pagerState.currentPage + 1) % items.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    // 监听页面变化
    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    Box(modifier = modifier) {
        YueHorizontalPager(
            pageCount = items.size,
            style = style,
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) { page ->
            items[page].content()
        }

        // 指示器
        YuePagerIndicator(
            pageCount = items.size,
            currentPage = currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

data class CarouselItem(
    val title: String = "",
    val content: @Composable () -> Unit
)

// 引导页组件
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YueOnboarding(
    pages: List<OnboardingPage>,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    var currentPage by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = pagerState.currentPage
    }

    val isLastPage = currentPage == pages.size - 1

    Column(modifier = modifier.fillMaxSize()) {
        YueHorizontalPager(
            pageCount = pages.size,
            modifier = Modifier.weight(1f),
            style = YuePagerStyle.GLASS,
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(80.dp))
                        .yueGlowBackground(cornerRadius = 80.dp, intensity = 0.8f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = pages[page].icon,
                        contentDescription = pages[page].title,
                        tint = LocalYueThemeColors.current.glowColor,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = pages[page].title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = pages[page].description,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        // 底部区域
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            YuePagerIndicator(
                pageCount = pages.size,
                currentPage = currentPage
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (!isLastPage) {
                    com.yue.ultra.theme.components.YueOutlinedButton(
                        text = "跳过",
                        onClick = onFinish,
                        modifier = Modifier.weight(1f)
                    )
                }

                com.yue.ultra.theme.components.YueButton(
                    text = if (isLastPage) "开始使用" else "下一步",
                    onClick = {
                        if (isLastPage) {
                            onFinish()
                        } else {
                            // 滑动到下一页
                        }
                    },
                    modifier = Modifier.weight(1f),
                    icon = if (isLastPage) Icons.Default.Check else Icons.Default.ArrowForward
                )
            }
        }
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
