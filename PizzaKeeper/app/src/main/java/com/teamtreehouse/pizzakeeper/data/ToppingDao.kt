package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

// Recognition as a DAO
@Dao
interface ToppingDao {
    @Query("select * from topping")
    //    Queries for all the toppings
    fun getAll(): List<Topping>

    @Query("select * from topping where id = :id")
    fun getToppingById(id: Int): Topping

//    onConflict will throw an Error and replace the duplicate topping in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topping: Topping)
}