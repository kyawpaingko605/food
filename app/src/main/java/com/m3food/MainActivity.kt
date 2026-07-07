package com.m3food

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.m3food.ui.screens.CartItem
import com.m3food.ui.screens.CartScreen
import com.m3food.ui.screens.FoodDetailScreen
import com.m3food.ui.screens.FoodItem
import com.m3food.ui.screens.HomeScreen
import com.m3food.ui.theme.M3FoodTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3FoodTheme {
                val context = LocalContext.current
                var currentScreen by remember { mutableStateOf("home") }
                var selectedFoodItem by remember { mutableStateOf<FoodItem?>(null) }
                var userLocation by remember { mutableStateOf("ဗဟန်းမြို့နယ်၊ ရန်ကုန်မြို့") }
                
                // ခြင်းတောင်းထဲရှိ တကယ့်ပစ္စည်းစာရင်းများကို သိမ်းဆည်းရန် MutableStateList
                val cartItems = remember { mutableStateListOf<CartItem>() }

                // GPS Permission တောင်းခံရန် စနစ်
                val locationPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        userLocation = "လှိုင်မြို့နယ်၊ ရန်ကုန်မြို့ (GPS ရရှိပါပြီ)"
                        Toast.makeText(context, "တည်နေရာ ရယူခွင့် ပေးလိုက်ပါပြီ", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "တည်နေရာ ရယူခွင့် ငြင်းပယ်ခံရပါသည်", Toast.LENGTH_SHORT).show()
                    }
                }

                // အစားအသောက်စာရင်း
                val foodList = remember {
                    listOf(
                        FoodItem("1", "ရွှေမုန့်ဟင်းခါး", "Royal Golden Mohinga", 2000.0, 4.9, "ရွှေဘိုဆန်အစစ်ဖြင့် ပြုလုပ်ထားသော နန်းပြား မုန့်ဟင်းခါး ဖြစ်ပါသည်။", 0),
                        FoodItem("2", "ရှမ်းခေါက်ဆွဲ", "Traditional Shan Noodles", 2500.0, 4.8, "ရှမ်းရိုးရာ အရသာစစ်စစ် ရှမ်းခေါက်ဆွဲ ပူပူနွေးနွေးလေး ဖြစ်ပါသည်။", 0),
                        FoodItem("3", "လက်ဖက်သုပ်", "Heritage Tea Leaf Salad", 3000.0, 4.9, "မန္တလေးဇယန်းလက်ဖက်နှင့် တွဲဖက်သုပ်ထားသော လက်ဖက်သုပ် ဖြစ်ပါသည်။", 0),
                        FoodItem("4", "ရွှေရင်အေး", "Royal Shwe Yin Aye", 3500.0, 4.7, "အုန်းနို့ အနံ့မွှေးမွှေးလေးနှင့် ရွှေရင်အေး အေးအေးလေး ဖြစ်ပါသည်။", 0)
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentScreen == "home" || currentScreen == "detail",
                                onClick = { currentScreen = "home"; selectedFoodItem = null },
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
                                            // ခြင်းတောင်းထဲရှိ စုစုပေါင်းအရေအတွက် (Quantity) ကို တွက်ချက်ပြသခြင်း
                                            val totalQty = cartItems.sumOf { it.quantity }
                                            if (totalQty > 0) {
                                                Badge { Text(totalQty.toString()) }
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
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigationHost(
                            currentScreen = currentScreen,
                            selectedFoodItem = selectedFoodItem,
                            userLocation = userLocation,
                            foodList = foodList,
                            cartItems = cartItems,
                            onNavigate = { screen -> currentScreen = screen },
                            onFoodSelect = { food -> selectedFoodItem = food; currentScreen = "detail" },
                            onAddToCartClick = { food, qty ->
                                // ပစ္စည်းအဟောင်းရှိရင် အရေအတွက်ပဲတိုးပြီး မရှိရင် အသစ်ထည့်ခြင်း
                                val existingItem = cartItems.find { it.name == food.name }
                                if (existingItem != null) {
                                    val index = cartItems.indexOf(existingItem)
                                    cartItems[index] = existingItem.copy(quantity = existingItem.quantity + qty)
                                } else {
                                    cartItems.add(CartItem(name = food.name, price = food.price.toInt(), quantity = qty))
                                }
                            },
                            onRemoveCartItem = { item -> cartItems.remove(item) },
                            onLocationClick = {
                                val checkPermission = ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.ACCESS_FINE_LOCATION
                                )
                                if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                                    userLocation = "လှိုင်မြို့နယ်၊ ရန်ကုန်မြို့"
                                    Toast.makeText(context, "တည်နေရာကို အပ်ဒိတ်လုပ်ပြီးပါပြီ", Toast.LENGTH_SHORT).show()
                                } else {
                                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            }
                        )
                    }
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
    userLocation: String,
    foodList: List<FoodItem>,
    cartItems: List<CartItem>,
    onNavigate: (String) -> Unit,
    onFoodSelect: (FoodItem) -> Unit,
    onAddToCartClick: (FoodItem, Int) -> Unit,
    onRemoveCartItem: (CartItem) -> Unit,
    onLocationClick: () -> Unit
) {
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()) {
        when (currentScreen) {
            "home" -> {
                HomeScreen(
                    currentLocation = userLocation,
                    foodList = foodList,
                    onFoodClick = onFoodSelect,
                    onAddToCart = { food ->
                        onAddToCartClick(food, 1)
                        Toast.makeText(context, "${food.name} ကို ခြင်းတောင်းထဲ ထည့်ပြီးပါပြီ", Toast.LENGTH_SHORT).show()
                    },
                    onLocationClick = onLocationClick
                )
            }
            "detail" -> {
                selectedFoodItem?.let { food ->
                    FoodDetailScreen(
                        foodName = food.name,
                        price = food.price,
                        description = food.description,
                        ingredients = listOf("ရိုးရာပါဝင်ပစ္စည်းများ"),
                        onBackClick = { onNavigate("home") },
                        onAddToCartClick = { qty, toppings ->
                            onAddToCartClick(food, qty)
                            Toast.makeText(context, "${food.name} ($qty) ခုကို ခြင်းတောင်းထဲ ထည့်ပြီးပါပြီ", Toast.LENGTH_SHORT).show()
                            onNavigate("home")
                        }
                    )
                }
            }
            "menu" -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("မီနူးစာမျက်နှာ ဖြစ်သည်") }
            
            // အဖြူရောင် Screen နေရာတွင် စနစ်တကျ ချိတ်ဆက်လိုက်သော တကယ့် CartScreen
            "cart" -> {
                CartScreen(
                    cartItems = cartItems,
                    onRemoveItem = onRemoveCartItem,
                    onCheckoutClick = {
                        Toast.makeText(context, "မှာယူမှု အောင်မြင်ပါသည်။ ဆိုင်မှ မကြာမီ ဆက်သွယ်ပါမည်။", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}
