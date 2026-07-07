package com.m3food

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                
                // တည်နေရာပြောင်းလဲမှုကို သိမ်းဆည်းရန် State
                var userLocation by remember { mutableStateOf("ဗဟန်းမြို့နယ်၊ ရန်ကုန်မြို့") }
                
                // ခြင်းတောင်းထဲရှိ စုစုပေါင်းပစ္စည်းအရေအတွက် State
                var cartItemsCount by remember { mutableStateOf(0) }

                // အစားအသောက် စာရင်းရင်းမြစ် (ပုံများထည့်လိုပါက res/drawable ထဲ ပုံထည့်ပြီး 0 နေရာတွင် R.drawable.your_image_name ဟုပြောင်းပါ)
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
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            "home" -> HomeScreen(
                                currentLocation = userLocation,
                                foodList = foodList,
                                onFoodClick = { food -> selectedFoodItem = food; currentScreen = "detail" },
                                onAddToCart = { food ->
                                    cartItemsCount += 1 // ခြင်းတောင်းထဲထည့်လျှင် Badge အရေအတွက် တိုးသွားမည်
                                    Toast.makeText(context, "${food.name} ကို ခြင်းတောင်းထဲ ထည့်ပြီးပါပြီ", Toast.LENGTH_SHORT).show()
                                },
                                onLocationClick = {
                                    userLocation = "လှိုင်မြို့နယ်၊ ရန်ကုန်မြို့" // တည်နေရာပြောင်းလဲမှု စမ်းသပ်ရန်
                                    Toast.makeText(context, "တည်နေရာ ပြောင်းလဲပြီးပါပြီ", Toast.LENGTH_SHORT).show()
                                }
                            )
                            "detail" -> selectedFoodItem?.let { food ->
                                FoodDetailScreen(
                                    foodName = food.name,
                                    price = food.price,
                                    description = food.description,
                                    ingredients = listOf("ရိုးရာပါဝင်ပစ္စည်းများ"),
                                    onBackClick = { currentScreen = "home" },
                                    onAddToCartClick = { qty, toppings ->
                                        cartItemsCount += qty // Detail Screen မှ အရေအတွက်အလိုက် မှာယူမှု ပေါင်းထည့်ခြင်း
                                        Toast.makeText(context, "${food.name} ($qty) ခုကို ခြင်းတောင်းထဲ ထည့်ပြီးပါပြီ", Toast.LENGTH_SHORT).show()
                                        currentScreen = "home"
                                    }
                                )
                            }
                            "menu" -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("မီနူးစာမျက်နှာ ဖြစ်သည်") }
                            "cart" -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("ခြင်းတောင်းထဲတွင် မှာယူထားသော ပစ္စည်း ($cartItemsCount) ခု ရှိပါသည်") }
                        }
                    }
                }
            }
        }
    }
}
