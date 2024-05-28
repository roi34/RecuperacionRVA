package com.liceolapaz.des.RVA.constants

class DbConstants {
    companion object {
        const val DBNAME = "users.db"
        const val DB_VERSION = 1
        const val CREATE_USERS_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS users (
            email TEXT NOT NULL PRIMARY KEY,
            password TEXT NOT NULL,
            language TEXT NOT NULL,
            age INTEGER NOT NULL,
            name TEXT NOT NULL
            )"""

        const val INSERT_ADMIN_USER_STATEMENT = """
            INSERT INTO users (email, password, language, age, name)
            SELECT 'admin', 'liceo', 'Español (ES)', 28, 'admin'
            WHERE NOT EXISTS (
            SELECT 1 FROM users WHERE email = 'admin'
            )"""

        const val DELETE_USERS_TABLE_STATEMENT = "DROP TABLE IF EXISTS users"

        const val USERS_TABLE = "users"
        const val EMAIL_COLUMN = "email"
    }
}