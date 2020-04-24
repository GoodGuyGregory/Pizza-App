package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

//Adds Room Entity
@Entity
data class Topping (
        @PrimaryKey
        val id: Int,
        val name: String,
        val drawableName: String
)