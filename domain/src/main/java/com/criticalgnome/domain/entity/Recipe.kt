package com.criticalgnome.domain.entity

data class Recipe(
    val id: Long,
    val title: String,
    val description: String?,
    val comment: String?,
    val rating: Int,
    val isFavorite: Boolean,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
    val photos: List<String>
)
