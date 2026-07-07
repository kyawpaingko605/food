package com.m3food.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FoodLightColorScheme = lightColorScheme(
    primary = Color(0xFFE65100),       // ပုံထဲကအတိုင်း လိမ္မော်ရောင်တောက်တောက်
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFE0B2),
    onPrimaryContainer = Color(0xFFB71C1C),
    background = Color(0xFFFAFAFA),    // နောက်ခံ အဖြူဆန်းသန့်သန့်
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF757575)
)

@Composable
fun M3FoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FoodLightColorScheme, // ပုံစံတူ ကာလာအတိုင်း Force သုံးခိုင်းခြင်း
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
