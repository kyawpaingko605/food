package com.m3food.model

/**
 * Jetpack Compose နှင့် ပိုမိုအဆင်ပြေစေရန် Kotlin Data Class အဖြစ် 
 * ကျစ်လျစ်စွာ ပြန်လည်ပြင်ဆင်ထားသော FoodItem Model ဖြစ်သည်။
 */
data class FoodItem(
    val id: String,
    val name: String,
    val nameMy: String? = null,
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val category: String? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val descriptionMy: String? = null,
    val preparationTime: String? = null,
    val ingredients: List<String> = emptyList()
) {
    init {
        require(id.isNotBlank()) { "ID မဖြစ်မနေ ပါရပါမည်။" }
        require(name.isNotBlank()) { "Name မဖြစ်မနေ ပါရပါမည်။" }
    }
}
