package com.m3food.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class FoodItem(
    val id: String,
    val name: String,
    val englishName: String,
    val price: Double,
    val rating: Double,
    val description: String,
    val imageRes: Int // drawable ထဲက ပုံ Resource ID ထည့်ရန်
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentLocation: String,
    foodList: List<FoodItem>,
    onFoodClick: (FoodItem) -> Unit,
    onAddToCart: (FoodItem) -> Unit,
    onLocationClick: () -> Unit
) {
    val categories = listOf("အားလုံး", "မြန်မာစာ", "ခေါက်ဆွဲများ", "အချိုပွဲ")
    var selectedCategory by remember { mutableStateOf("အားလုံး") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        
        // ၁။ တည်နေရာပြသသည့်အပိုင်း (Top Location Card)
        Card(
            onClick = onLocationClick,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("ပို့ဆောင်မည့် တည်နေရာ", style = MaterialTheme.typography.labelSmall)
                    Text(currentLocation, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("အမျိုးအစားများ", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    shape = CircleShape
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("လူကြိုက်အများဆုံး မီနူးများ", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        // ၂။ အစားအသောက် Grid List (ဘေးတိုက် ၂ ခုစီ ပြသရန်)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(foodList) { food ->
                FoodCard(
                    food = food, 
                    onClick = { onFoodClick(food) }, 
                    onAddClick = { onAddToCart(food) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCard(food: FoodItem, onClick: () -> Unit, onAddClick: () -> Unit) {
    ElevatedCard(onClick = onClick, shape = MaterialTheme.shapes.large) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                // အစားအသောက်ပုံ ထည့်သွင်းရန်နေရာ
                if (food.imageRes != 0) {
                    Image(
                        painter = painterResource(id = food.imageRes),
                        contentDescription = food.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().padding(8.dp).clip(MaterialTheme.shapes.medium)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Image", style = MaterialTheme.typography.labelSmall)
                    }
                }
                
                // Rating Star
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = CircleShape,
                    modifier = Modifier.padding(12.dp).align(Alignment.TopStart)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
                        Text(food.rating.toString(), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(food.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(food.englishName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Ks ${String.format("%,.0f", food.price)}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    
                    // (+) ခလုတ်နှိပ်လျှင် မှာယူမှုထဲ တိုက်ရိုက်ပေါင်းထည့်ရန်
                    FilledIconButton(onClick = onAddClick, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }
    }
}
