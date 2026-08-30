package com.yue.ultra.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yue.ultra.theme.effects.*
import com.yue.ultra.theme.theme.LocalYueThemeColors

/**
 * 极致Yue Ultra 透明输入框
 * 表面泛光，2048x级别光影效果
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YueEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        color = Color.White,
        fontSize = 16.sp
    ),
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cornerRadius: Int = 24,
    showAnimatedShine: Boolean = true
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val colors = LocalYueThemeColors.current

    val animatedGlowIntensity by animateFloatAsState(
        targetValue = if (isFocused) 1f else 0.6f,
        animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic),
        label = "glowIntensity"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) colors.glowColor else colors.primaryContainer.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 300),
        label = "borderColor"
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .yueGlowBackground(
                    cornerRadius = cornerRadius.dp,
                    intensity = animatedGlowIntensity
                )
                .then(
                    if (showAnimatedShine && isFocused) {
                        Modifier.yueAnimatedShine(cornerRadius = cornerRadius.dp)
                    } else Modifier
                )
                .clip(RoundedCornerShape(cornerRadius.dp))
                .border(
                    width = if (isFocused) 2.dp else 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(cornerRadius.dp)
                )
                .yueInnerShadow(cornerRadius = cornerRadius.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        leadingIcon()
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = if (leadingIcon != null) 8.dp else 20.dp,
                            end = if (trailingIcon != null) 8.dp else 20.dp,
                            top = 14.dp,
                            bottom = 14.dp
                        )
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(FocusRequester()),
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = textStyle.copy(
                            color = if (isError) Color(0xFFFFB4AB) else textStyle.color
                        ),
                        cursorBrush = SolidColor(colors.glowColor),
                        visualTransformation = visualTransformation,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        minLines = minLines,
                        interactionSource = interactionSource,
                        decorationBox = { innerTextField ->
                            Box {
                                if (value.isEmpty() && placeholder != null) {
                                    Text(
                                        text = placeholder,
                                        color = Color.White.copy(alpha = 0.4f),
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                if (trailingIcon != null) {
                    Box(
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        trailingIcon()
                    }
                }
            }
        }

        if (isError) {
            Text(
                text = "输入错误",
                color = Color(0xFFFFB4AB),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}

// 搜索框变体
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YueSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "搜索...",
    onSearch: (String) -> Unit = {}
) {
    YueEditText(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = placeholder,
        singleLine = true,
        cornerRadius = 28,
        leadingIcon = {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Search,
                contentDescription = "搜索",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = if (query.isNotEmpty()) {
            {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Clear,
                        contentDescription = "清除",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else null,
        keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(query) }
        )
    )
}

// 密码输入框变体
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YuePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = "密码",
    placeholder: String = "请输入密码"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    YueEditText(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else androidx.compose.ui.text.input.PasswordVisualTransformation(),
        keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(
            keyboardType = androidx.compose.ui.text.input.KeyboardType.Password
        ),
        leadingIcon = {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Lock,
                contentDescription = "密码",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible)
                        androidx.compose.material.icons.Icons.Filled.Visibility
                    else
                        androidx.compose.material.icons.Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "隐藏密码" else "显示密码",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    )
}
