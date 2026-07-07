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
    primary = Color(0xFF6750A4),       // ပုံထဲက 'အားလုံး' ခလုတ်ကဲ့သို့ ခရမ်းရင့်ရောင်
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF), // ပုံထဲက နောက်ခံ ခရမ်းနုရောင်ဖျော့ဖျော့
    onPrimaryContainer = Color(0xFF21005D),
    background = Color(0xFFFEF7FF),    // ပုံထဲက App တစ်ခုလုံးရဲ့ Background ခရမ်းဖြူရောင်စစ်စစ်
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE7E0EC), // Filter Chip တွေရဲ့ ရွေးမထားတဲ့ ခရမ်းပြာနုရောင်နောက်ခံ
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFB3261E)          // နှလုံးသား (Favorite) Icon အတွက် အရောင်
)

@Composable
fun M3FoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FoodLightColorScheme, // ပုံထဲက ကာလာအတိုင်း ၁၀၀% Force သုံးခိုင်းခြင်း
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
