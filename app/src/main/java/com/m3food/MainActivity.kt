package com.m3food

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.m3food.ui.theme.M3FoodTheme

class MainActivity : ComponentActivity() {
    // Experimental API အမှားကို ကျော်ဖြတ်ရန် Annotation ၂ ခုလုံး ထည့်ပေးထားပါသည်
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3FoodTheme {
                var currentScreen by remember { mutableStateOf("home") }
                var cartItemsCount by remember { mutableStateOf(3) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentScreen == "home",
                                onClick = { currentScreen = "home" },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("ပင်မစာမျက်နှာ") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "menu",
                                onClick = { currentScreen = "menu" },
                                icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                                label = { Text("မီနူး") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "cart",
                                onClick = { currentScreen = "cart" },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (cartItemsCount > 0) {
                                                Badge { Text(cartItemsCount.toString()) }
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                                    }
                                },
                                label = { Text("ခြင်းတောင်း") }
                            )
                        }
                    }
                ) { innerPadding ->
                    AppNavigationHost(
                        modifier = Modifier.padding(innerPadding),
                        currentScreen = currentScreen,
                        onNavigate = { screen -> currentScreen = screen }
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigationHost(
    modifier: Modifier = Modifier,
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "ယခုစာမျက်နှာမှာ - $currentScreen ဖြစ်သည်")
    }
}
