package com.example.shopapp.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.shopapp.model.data.CartItem
import java.lang.Exception

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SHOPPING_CART =
            """CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY , 
            $KEY_IMAGE TEXT, $KEY_NAME TEXT, $KEY_AMOUNT INTEGER, $KEY_PRICE DOUBLE)""".trimIndent()

        db?.execSQL(CREATE_SHOPPING_CART)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }

    fun addItem(item: CartItem): Long {
        val amount = checkIfItemExists(item.itemID)

        if (amount == 0) {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(KEY_ID, item.itemID)
            contentValues.put(KEY_NAME, item.itemName)
            contentValues.put(KEY_IMAGE, item.itemImage)
            contentValues.put(KEY_PRICE, item.itemPrice)
            contentValues.put(KEY_AMOUNT, item.itemAmount)

            val success = db.insert(TABLE_NAME, null, contentValues)

            db.close()
            return success
        } else {
            return updateItem(item, amount).toLong()
        }
    }

    @SuppressLint("Recycle")
    fun getItems(): List<CartItem> {
        val cart: ArrayList<CartItem> = ArrayList()

        val sql = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(sql, null)
        } catch (e: Exception) {
            db.execSQL(sql)
            return ArrayList()
        }
        var itemID : Int = 0
        var itemName: String = ""
        var itemPrice: Double = 0.0
        var itemAmount: Int = 0
        var itemImage: String = ""
        if (cursor.moveToFirst()) {
            do {
                try {
                    itemID = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                    itemName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                    itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PRICE))
                    itemAmount = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AMOUNT))
                    itemImage = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE))
                } catch (e: Exception) {
                    Log.d("DatabaseHandler", "getItems: Exception when getting items")
                }

                val item = CartItem(itemID, itemImage, itemName, itemPrice, itemAmount)
                cart.add(item)
            } while (cursor.moveToNext())
        }
        return cart
    }

    fun deleteItem(item: CartItem): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, item.itemID)

        val success = db.delete(TABLE_NAME, "ID = " + item.itemID, null)
        db.close()
        return success
    }

    fun emptyCart() {
        val db = this.writableDatabase
        val sql = "DELETE FROM $TABLE_NAME"

        db.execSQL(sql)
        db.close()
    }

    private fun checkIfItemExists(itemID: Int) : Int {
        val sql = "SELECT 1 FROM $TABLE_NAME WHERE $KEY_ID = $itemID"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(sql, null)
        } catch (e: Exception) {
            db.execSQL(sql)
            return 0
        }
        var itemAmount: Int = 0
        if (cursor.moveToFirst()) {
            try {
                itemAmount = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AMOUNT))
            } catch (e: Exception) {
                Log.d("DatabaseHandler", "getItems: Exception when getting items")
            }
        }
        return itemAmount
    }

    private fun updateItem(cartItem: CartItem, amount: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, cartItem.itemID)
        contentValues.put(KEY_NAME, cartItem.itemName)
        contentValues.put(KEY_IMAGE, cartItem.itemImage)
        contentValues.put(KEY_PRICE, cartItem.itemPrice)
        contentValues.put(KEY_AMOUNT, cartItem.itemAmount + amount)

        val success = db.update(TABLE_NAME, contentValues, "id=" + cartItem.itemID, null)

        db.close()
        return success
    }


    companion object {
        const val DATABASE_NAME = "SHOPPING CART APP"
        const val TABLE_NAME = "ShoppingCart"
        const val KEY_ID = "id"
        const val KEY_IMAGE = "image"
        const val KEY_NAME = "name"
        const val KEY_PRICE = "price"
        const val KEY_AMOUNT = "amount"

    }
}