package com.zerosword.resources.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.Red,
    tertiary = Color.Black,
    onPrimary = Color.Black,  // primary 배경 위의 텍스트 색상
    onSecondary = Color.White,  // secondary 배경 위의 텍스트 색상
    onTertiary = Color.White,  // tertiary 배경 위의 텍스트 색상
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    secondary = Color(0xFF8B0000),  // Dark Red
    tertiary = Color.LightGray,
    onPrimary = Color.White,  // primary 배경 위의 텍스트 색상
    onSecondary = Color.White,  // secondary 배경 위의 텍스트 색상
    onTertiary = Color.Black,  // tertiary 배경 위의 텍스트 색상
    background = Color.Black,
    surface = Color.DarkGray,
    onBackground = Color.White,
    onSurface = Color.White
)