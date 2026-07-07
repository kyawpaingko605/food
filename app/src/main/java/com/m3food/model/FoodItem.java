package com.m3food.model

/**
 * Kotlin Data Class အဖြစ် ပြောင်းလဲထားသော FoodItem Model ဖြစ်သည်။
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
