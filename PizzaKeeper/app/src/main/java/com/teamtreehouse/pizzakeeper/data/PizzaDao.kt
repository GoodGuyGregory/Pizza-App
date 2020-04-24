package com.teamtreehouse.pizzakeeper.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PizzaDao {
    @Query("select * from pizza")
    //    Queries for all the toppings
    fun getAll(): LiveData<List<Pizza>>

    @Query("select * from pizza where id = :id")
    fun getPizzaById(id: Int): Pizza

//    Long returned to add the unique Id
    @Insert
    fun insert(pizza: Pizza): Long

    @Query("delete from pizza where id = :id")
    fun deletePizzaById(id: Int)

}