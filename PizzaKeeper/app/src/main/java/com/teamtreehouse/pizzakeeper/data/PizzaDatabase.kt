package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

//Creates Database for Pizzas
@Database(entities = [Pizza::class, PizzaTopping::class, Topping::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class PizzaDatabase: RoomDatabase() {
//    gives Access to the DAOs
    abstract fun PizzaDao(): PizzaDao

    abstract fun PizzaToppingDao(): PizzaToppingDao

    abstract  fun ToppingDao(): ToppingDao

}