package com.liceolapaz.des.RVA.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.liceolapaz.des.RVA.constants.DbConstants

class SQLiteHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DbConstants.CREATE_USERS_TABLE_STATEMENT)
        db.execSQL(DbConstants.INSERT_ADMIN_USER_STATEMENT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DbConstants.DELETE_USERS_TABLE_STATEMENT)
        onCreate(db)
    }
}