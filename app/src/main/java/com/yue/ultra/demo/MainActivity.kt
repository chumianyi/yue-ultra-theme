package com.yue.ultra.demo

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import com.yue.ultra.theme.YueThemeConfig
import com.yue.ultra.theme.anim.YueSwipeTransformer
import com.yue.ultra.theme.util.YueThemeUtils
import com.yue.ultra.theme.widget.YueEditText
import com.yue.ultra.theme.widget.YueNavigationBar
import com.yue.ultra.theme.widget.YueSpinner

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var navigationBar: YueNavigationBar
    private lateinit var colorSpinner: YueSpinner
    private lateinit var styleSpinner: YueSpinner
    private lateinit var testEditText: YueEditText

    private val colors = listOf("紫色主题", "绿色主题", "蓝色主题", "金色主题", "红色主题")
    private val styles = listOf("玻璃拟态", "深度效果", "缩放效果", "堆叠效果", "立方体效果")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 应用全屏主题
        YueThemeUtils.applyFullscreenTheme(this)

        // 初始化View
        viewPager = findViewById(R.id.viewPager)
        navigationBar = findViewById(R.id.navigationBar)
        colorSpinner = findViewById(R.id.colorSpinner)
        styleSpinner = findViewById(R.id.styleSpinner)
        testEditText = findViewById(R.id.testEditText)

        // 设置默认紫色主题
        YueThemeUtils.applyPurpleTheme()

        // 初始化ViewPager
        setupViewPager()

        // 初始化导航栏
        setupNavigationBar()

        // 初始化颜色选择器
        setupColorSpinner()

        // 初始化动画风格选择器
        setupStyleSpinner()

        // 设置测试输入框
        testEditText.hint = "输入测试文字..."
    }

    private fun setupViewPager() {
        val fragments = listOf(
            createPage("页面 1", "极致Yue Ultra\n超丝滑滑动切换\n表面泛着光\n2048x级别光影"),
            createPage("页面 2", "透明导航栏\n透明输入框\n透明选择框\n几乎支持所有东西"),
            createPage("页面 3", "默认紫色主题\n可调整绿色等其他颜色\n绚丽到让人无法自拔\n当然也是很吃性能的")
        )

        viewPager.adapter = object : RecyclerView.Adapter<PageViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
                val textView = TextView(parent.context)
                textView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                textView.gravity = Gravity.CENTER
                textView.textSize = 20f
                textView.setTextColor(Color.WHITE)
                textView.setPadding(48, 48, 48, 48)
                return PageViewHolder(textView)
            }

            override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
                (holder.itemView as TextView).text = fragments[position]
            }

            override fun getItemCount(): Int = fragments.size
        }

        // 应用丝滑滑动动画
        YueSwipeTransformer.applyTo(viewPager, YueSwipeTransformer.AnimationStyle.GLASS)
    }

    private fun createPage(title: String, content: String): String {
        return "$title\n\n$content"
    }

    private fun setupNavigationBar() {
        val items = listOf(
            YueNavigationBar.NavItem("首页"),
            YueNavigationBar.NavItem("发现"),
            YueNavigationBar.NavItem("设置")
        )
        navigationBar.setItems(items)
        navigationBar.setOnItemClickListener { index ->
            viewPager.currentItem = index
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigationBar.setSelectedIndex(position)
            }
        })
    }

    private fun setupColorSpinner() {
        colorSpinner.setYueAdapter(colors) { item, position ->
            when (position) {
                0 -> YueThemeUtils.applyPurpleTheme()
                1 -> YueThemeUtils.applyGreenTheme()
                2 -> YueThemeUtils.applyBlueTheme()
                3 -> YueThemeUtils.applyGoldTheme()
                4 -> YueThemeUtils.applyRedTheme()
            }
            recreate()
        }
    }

    private fun setupStyleSpinner() {
        styleSpinner.setYueAdapter(styles) { item, position ->
            val style = when (position) {
                0 -> YueSwipeTransformer.AnimationStyle.GLASS
                1 -> YueSwipeTransformer.AnimationStyle.DEPTH
                2 -> YueSwipeTransformer.AnimationStyle.ZOOM
                3 -> YueSwipeTransformer.AnimationStyle.STACK
                4 -> YueSwipeTransformer.AnimationStyle.CUBE
                else -> YueSwipeTransformer.AnimationStyle.GLASS
            }
            YueSwipeTransformer.applyTo(viewPager, style)
        }
    }

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
