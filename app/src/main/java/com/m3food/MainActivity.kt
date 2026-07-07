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
import com.m3food.ui.screens.FoodDetailScreen
import com.m3food.ui.screens.FoodItem
import com.m3food.ui.screens.HomeScreen
import com.m3food.ui.theme.M3FoodTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // dynamicColor အဖြူရောင်ထပ်ပြီး စာသားမမြင်ရသည့်ပြဿနာအတွက် false ဟု ပိတ်ထားပါသည်
            M3FoodTheme(dynamicColor = false) {
                var currentScreen by remember { mutableStateOf("home") }
                var cartItemsCount by remember { mutableStateOf(3) }
                
                // ရွေးချယ်လိုက်သော အစားအသောက် Data ကို မှတ်ထားရန် State
                var selectedFoodItem by remember { mutableStateOf<FoodItem?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentScreen == "home" || currentScreen == "detail",
                                onClick = { 
                                    selectedFoodItem = null
                                    currentScreen = "home" 
                                },
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
                        selectedFoodItem = selectedFoodItem,
                        onNavigate = { screen -> currentScreen = screen },
                        onFoodSelect = { food ->
                            selectedFoodItem = food
                            currentScreen = "detail" // အသေးစိတ်စာမျက်နှာသို့ သွားမည်
                        }
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
    selectedFoodItem: FoodItem?,
    onNavigate: (String) -> Unit,
    onFoodSelect: (FoodItem) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (currentScreen) {
            "home" -> {
                // အစားအသောက် Grid ပုံစံ ပေါ်လာစေရန် HomeScreen ကို ဤနေရာတွင် ချိတ်ဆက်ရပါမည်
                HomeScreen(
                    onFoodClick = { food -> onFoodSelect(food) }
                )
            }
            "detail" -> {
                // ကလစ်နှိပ်လိုက်သည့် ဟင်းအလိုက် Data ကို လှမ်းပြောင်းပေးခြင်း
                selectedFoodItem?.let { food ->
                    FoodDetailScreen(
                        foodName = food.name,
                        price = food.price,
                        description = food.description,
                        ingredients = listOf(),
                        onBackClick = { onNavigate("home") },
                        onAddToCartClick = { quantity, toppings -> 
                            // ခြင်းတောင်းထဲထည့်ရန်
                        }
                    )
                }
            }
            "menu" -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("မီနူးစာမျက်နှာ ဖြစ်သည်")
                }
            }
            "cart" -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("ခြင်းတောင်းစာမျက်နှာ ဖြစ်သည်")
                }
            }
        }
    }
}
