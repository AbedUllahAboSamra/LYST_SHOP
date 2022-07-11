package com.example.lyst_shop.db

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDataBase(var context: Context) :
    SQLiteOpenHelper(context, dataBaseNsme, null, dataVersion) {

    private lateinit var db: SQLiteDatabase

    init {
        db = writableDatabase
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE MYBAG(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "productId TEXT);"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS MYBAG")
        onCreate(db)
    }

    fun removeId(id: String) :Boolean{
        return db.delete("MYBAG", "productId = $id",null) > 0
    }


    fun addId(id: String): Boolean {
        var content = ContentValues()
        content.put("productId", id)
        return db.insert("MYBAG", null, content) > 0

    }


    fun getAllIds(): ArrayList<String> {
        var arr = ArrayList<String>()

        var curser = db.rawQuery("SELECT * FROM MYBAG", null)
        curser.moveToFirst()
        while (!curser.isAfterLast) {
            val id = curser.getInt(1)
            arr.add(id.toString())
            curser.moveToNext()
        }
        curser.close()
        return arr
    }











    //////////////////
    companion object {
        val dataBaseNsme = "MyDataBasess"
        val dataVersion = 6
    }


}





