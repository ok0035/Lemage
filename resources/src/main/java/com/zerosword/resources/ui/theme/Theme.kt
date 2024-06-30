package com.zerosword.resources.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = Color(0xFFD3E4CD), // 파스텔 톤의 Secondary 색상
    onSecondary = Color.Black,
    tertiary = Color(0xFFFF6F61), // 밝은 느낌의 Red
    onTertiary = Color.White,
    background = Color(0xFFF1F1F1),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

// 다크 테마 색상 팔레트
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    secondary = Color(0xFF2C2C2C),
    onSecondary = Color.White,
    tertiary = Color(0xFFFF6F61), // 밝은 느낌의 Red
    onTertiary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)