package com.m3food.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(
    foodName: String,
    price: Double,
    description: String,
    ingredients: List<String>,
    onBackClick: () -> Unit,
    onAddToCartClick: (quantity: Int, selectedToppings: List<String>) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    val toppings = listOf("အပိုအသား" to 1500.0, "ဥ" to 500.0, "နံနံပင်" to 200.0)
    val selectedToppings = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(foodName, style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to Favorites")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "စားချင်စဖွယ် အစားအသောက်ပုံရိပ်",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ks ${String.format("%,.0f", price)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedCard(
                        shape = CircleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Text("-", style = MaterialTheme.typography.titleMedium)
                            }
                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = { quantity++ }) {
                                Icon(Icons.Default.Add, contentDescription = "Increase")
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "အသေးစိတ်အချက်အလက်များ",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (ingredients.isNotEmpty()) {
                item {
                    Text(
                        text = "ပါဝင်ပစ္စည်းများ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ingredients.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                Text(
                    text = "အပိုထည့်သွင်းရန် (Toppings)",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(toppings) { (toppingName, toppingPrice) ->
                val isSelected = selectedToppings.contains(toppingName)
                Surface(
                    onClick = {
                        if (isSelected) selectedToppings.remove(toppingName)
                        else selectedToppings.add(toppingName)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(toppingName, style = MaterialTheme.typography.bodyLarge)
                        Text("+Ks ${String.format("%,.0f", toppingPrice)}", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }

            item {
                Button(
                    onClick = { onAddToCartClick(quantity, selectedToppings.toList()) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("ခြင်းတောင်းထဲသို့ထည့်မည်", style = MaterialTheme.typography.labelLarge)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
