package com.example.myapplication.components

import android.net.Uri
import androidx.compose.ui.tooling.preview.Preview


class Product(
    private var _name: String,
    private val _owner: String, //Egen user class?
    private var _description: String,
    private var _price: Double,
    private val _photos: MutableList<Uri?> = mutableListOf(),
    private var _location: String, //Egen location class?
    private var _category: String //Egen category class?
) {
    val name: String
        get() = _name

    val owner: String
        get() = _owner

    val description: String
        get() = _description

    val price: Double
        get() = _price

    val photos: List<Uri?>
        get() = _photos.toList()

    val location: String
        get() = _location

    val category: String
        get() = _category


    fun updateName(newName: String) {
        require(newName.isNotEmpty()) { "Produktet må ha et navn" }
        _name = newName
    }

    fun updateDescription(newDescription: String) {
        _description = newDescription
    }

    fun updatePrice(newPrice: Double) {
        require(newPrice >= 0) { "Prisen må være et positivt tall" }
        _price = newPrice
    }

    fun updateLocation(newLocation: String) {
        _location = newLocation
    }

    fun updateCategory(newCategory: String) {
        _category = newCategory
    }

    fun addPhoto(uri: Uri?) {
        _photos.add(uri)
    }

    fun removePhoto(uri: Uri?) {
        _photos.remove(uri)
    }

}