package com.example.casestudy1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ExpenseManager.db"
        private const val DATABASE_VERSION = 1

        // Bảng Categories
        const val TABLE_CATEGORIES = "Categories"
        const val COL_CAT_ID = "id"
        const val COL_CAT_NAME = "name"
        const val COL_CAT_TYPE = "type"

        // Bảng Transactions
        const val TABLE_TRANSACTIONS = "Transactions"
        const val COL_TRANS_ID = "id"
        const val COL_TRANS_TYPE = "type"
        const val COL_TRANS_CAT_ID = "category_ida"
        const val COL_TRANS_AMOUNT = "amount"
        const val COL_TRANS_DATE = "date"
        const val COL_TRANS_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Tạo bảng Categories
        val createCategoriesTable = """
            CREATE TABLE $TABLE_CATEGORIES (
                $COL_CAT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_CAT_NAME TEXT NOT NULL,
                $COL_CAT_TYPE TEXT NOT NULL
            )
        """.trimIndent()

        // Tạo bảng Transactions
        val createTransactionsTable = """
            CREATE TABLE $TABLE_TRANSACTIONS (
                $COL_TRANS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TRANS_TYPE TEXT NOT NULL,
                $COL_TRANS_CAT_ID INTEGER NOT NULL,
                $COL_TRANS_AMOUNT REAL NOT NULL,
                $COL_TRANS_DATE TEXT NOT NULL,
                $COL_TRANS_NOTE TEXT,
                FOREIGN KEY ($COL_TRANS_CAT_ID) REFERENCES $TABLE_CATEGORIES($COL_CAT_ID)
            )
        """.trimIndent()

        db?.execSQL(createCategoriesTable)
        db?.execSQL(createTransactionsTable)

        // Dữ liệu danh mục ban đầu
        db?.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME, $COL_CAT_TYPE) VALUES ('Ăn uống', 'EXPENSE')")
        db?.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME, $COL_CAT_TYPE) VALUES ('Di chuyển', 'EXPENSE')")
        db?.execSQL("INSERT INTO $TABLE_CATEGORIES ($COL_CAT_NAME, $COL_CAT_TYPE) VALUES ('Lương', 'INCOME')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }
}