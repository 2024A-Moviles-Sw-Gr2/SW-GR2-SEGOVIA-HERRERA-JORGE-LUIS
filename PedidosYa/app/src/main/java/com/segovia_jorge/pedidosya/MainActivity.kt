package com.segovia_jorge.pedidosya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var recyclerViewSuggestions: RecyclerView
    private lateinit var categoryAdapter: Item.ItemAdapter
    private lateinit var suggestionAdapter: Item.ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar RecyclerView para categorías
        val categoryList = listOf(
            Item(R.drawable.bebidas, "Bebidas"),
            Item(R.drawable.mascota, "Mascotas"),
            Item(R.drawable.medicina, "Farmacia")
        )

        recyclerViewCategories = findViewById(R.id.recycler_view_categories)
        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = Item.ItemAdapter(categoryList, Item.ItemAdapter.VIEW_TYPE_CATEGORY)
        recyclerViewCategories.adapter = categoryAdapter

        // Configurar RecyclerView para sugerencias
        val suggestionList = listOf(
            Item(R.drawable.kfc, "KFC", "$1.09"),
            Item(R.drawable.mcdonalds, "McDonald's", "$2.09"),
            Item(R.drawable.valdez, "Juan Valdez", "$1.89")
        )

        recyclerViewSuggestions = findViewById(R.id.recycler_view_suggestions)
        recyclerViewSuggestions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        suggestionAdapter = Item.ItemAdapter(suggestionList, Item.ItemAdapter.VIEW_TYPE_SUGGESTION)
        recyclerViewSuggestions.adapter = suggestionAdapter
    }
}
