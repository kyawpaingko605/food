package com.m3food.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Material 3 Color Palette (Foodie Warm Amber & Peach Accents)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB59D), // Warm Peach
    onPrimary = Color(0xFF5E1700),
    primaryContainer = Color(0xFF882A05),
    onPrimaryContainer = Color(0xFFFFDBCE),
    secondary = Color(0xFFE7BEAF),
    onSecondary = Color(0xFF442A20),
    secondaryContainer = Color(0xFF5D4035),
    onSecondaryContainer = Color(0xFFFFDBCE),
    background = Color(0xFF201A18),
    onBackground = Color(0xFFEDE0DC),
    surface = Color(0xFF201A18),
    onSurface = Color(0xFFEDE0DC),
    surfaceVariant = Color(0xFF53433E),
    onSurfaceVariant = Color(0xFFD8C2BB),
    outline = Color(0xFFA08D87)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA93D13), // Deep Foodie Terracotta/Amber
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDBCE),
    onPrimaryContainer = Color(0xFF3B0A00),
    secondary = Color(0xFF77574E),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDBCE),
    onSecondaryContainer = Color(0xFF2C1611),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF201A18),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF201A18),
    surfaceVariant = Color(0xFFF5DED6),
    onSurfaceVariant = Color(0xFF53433E),
    outline = Color(0xFF85736C)
)

@Composable
fun M3FoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES_S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Custom typography using custom fonts
        shapes = Shapes,         // M3 Rounding (rounded-3xl, rounded-full)
        content = content
    )
}