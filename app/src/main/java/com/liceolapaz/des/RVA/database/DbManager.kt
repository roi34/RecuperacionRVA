package com.liceolapaz.des.RVA.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.liceolapaz.des.RVA.constants.DbConstants
import com.liceolapaz.des.RVA.models.User

class DbManager private constructor(context: Context) {
    private val sqLiteHelper: SQLiteHelper = SQLiteHelper(
        context,
        DbConstants.DBNAME,
        null,
        DbConstants.DB_VERSION
    )
    private var db: SQLiteDatabase? = null

    companion object {
        private var dbManager: DbManager? = null

        @Synchronized
        fun getInstance(context: Context): DbManager {
            if (dbManager == null) {
                dbManager = DbManager(context)
            }
            return dbManager!!
        }
    }

    private fun ensureDbIsOpen() {
        if (db == null || db?.isOpen != true) {
            db = sqLiteHelper.writableDatabase
        }
    }

    fun getAllUsers(): List<User> {
        ensureDbIsOpen()

        val usersList: MutableList<User> = mutableListOf()
        val cursor: Cursor? = db?.query(
            DbConstants.USERS_TABLE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val email = cursor.getString(0)
                val password = cursor.getString(1)
                val language = cursor.getString(2)
                val age = cursor.getInt(3)
                val name = cursor.getString(4)

                val user = User(email, password, language, age, name)
                usersList.add(user)
            }
            cursor.close()
        }

        db?.close()

        return usersList
    }

    fun getUserByEmail(userEmail: String): User? {
        ensureDbIsOpen()

        var user: User? = null
        val cursor: Cursor? = db?.query(
            DbConstants.USERS_TABLE,
            null,
            "${DbConstants.EMAIL_COLUMN} = ?",
            arrayOf(userEmail),
            null,
            null,
            null
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val email = cursor.getString(0)
                val password = cursor.getString(1)
                val language = cursor.getString(2)
                val age = cursor.getInt(3)
                val name = cursor.getString(4)

                user = User(email, password, language, age, name)
            }
            cursor.close()
        }

        db?.close()

        return user
    }

    fun insertUserIntoDb(userInfo: ContentValues) {
        ensureDbIsOpen()

        db?.insert(DbConstants.USERS_TABLE, null, userInfo)
        db?.close()
    }

    fun updateUser(email: String, userInfo: ContentValues) {
        ensureDbIsOpen()

        db?.update(
            DbConstants.USERS_TABLE,
            userInfo,
            "${DbConstants.EMAIL_COLUMN} = ?",
            arrayOf(email)
        )

        db?.close()
    }

    fun deleteUser(userEmail: String) {
        ensureDbIsOpen()

        db?.delete(
            DbConstants.USERS_TABLE,
            "${DbConstants.EMAIL_COLUMN} = ?",
            arrayOf(userEmail)
        )

        db?.close()
    }

    fun insertAdminUser() {
        ensureDbIsOpen()

        db?.execSQL(DbConstants.INSERT_ADMIN_USER_STATEMENT)

        db?.close()
    }
}
