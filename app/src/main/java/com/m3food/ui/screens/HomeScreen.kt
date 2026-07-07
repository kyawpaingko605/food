package com.m3food.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class FoodItem(
    val id: String,
    val name: String,
    val englishName: String,
    val price: Double,
    val rating: Double,
    val description: String,
    val imageRes: Int
)

// Error ပျောက်စေရန် ဤနေရာတွင် `@OptIn` ထည့်သွင်းထားပါသည်
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFoodClick: (FoodItem) -> Unit
) {
    val foodList = remember {
        listOf(
            FoodItem("1", "ရွှေမုန့်ဟင်းခါး", "Royal Golden Mohinga", 2000.0, 4.9, "ရွှေဘိုဆန်အစစ်ဖြင့် ပြုလုပ်ထားသော နန်းပြား မုန့်ဟင်းခါး ဖြစ်ပါသည်။", 0),
            FoodItem("2", "ရှမ်းခေါက်ဆွဲ", "Traditional Shan Noodles", 2500.0, 4.8, "ရှမ်းရိုးရာ အရသာစစ်စစ် ရှမ်းခေါက်ဆွဲ ပူပူနွေးနွေးလေး ဖြစ်ပါသည်။", 0),
            FoodItem("3", "လက်ဖက်သုပ်", "Heritage Tea Leaf Salad", 3000.0, 4.9, "မန္တလေးဇယန်းလက်ဖက်နှင့် တွဲဖက်သုပ်ထားသော လက်ဖက်သုပ် ဖြစ်ပါသည်။", 0),
            FoodItem("4", "ရွှေရင်အေး/ရွှေဖလူဒါ", "Royal Shwe Falooda", 3500.0, 4.7, "အုန်းနို့ အနံ့မွှေးမွှေးလေးနှင့် ရွှေဖလူဒါ အေးအေးလေး ဖြစ်ပါသည်။", 0)
        )
    }

    val categories = listOf("အားလုံး", "မြန်မာစာ", "ခေါက်ဆွဲများ", "အချိုပွဲ")
    var selectedCategory by remember { mutableStateOf("အားလုံး") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "အမျိုးအစားများ",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    shape = CircleShape
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "လူကြိုက်အများဆုံး မီနူးများ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "အရည်အသွေးမြင့် ရိုးရာနှင့် လူကြိုက်များသော ဟင်းလျာများ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            TextButton(onClick = {}) {
                Text("ကြည့်မည်")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(foodList) { food ->
                FoodCard(food = food, onClick = { onFoodClick(food) })
            }
        }
    }
}

// Error ပျောက်စေရန် ဤနေရာတွင်လည်း `@OptIn` ထည့်သွင်းထားပါသည်
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCard(
    food: FoodItem,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Food Image", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = food.rating.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = food.englishName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ks ${String.format("%,.0f", food.price)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    FilledIconButton(
                        onClick = onClick,
                        modifier = Modifier.size(32.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
