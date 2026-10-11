package com.example.casestudy1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class TransactionItem(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val type: String, // "Income" hoặc "Expense"
    val date: String,
    val category: String
)

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
        const val COL_TRANS_TITLE = "title"
        const val COL_TRANS_AMOUNT = "amount"
        const val COL_TRANS_TYPE = "type"
        const val COL_TRANS_DATE = "date"
        const val COL_TRANS_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCategoriesTable = ("CREATE TABLE " + TABLE_CATEGORIES + " ("
                + COL_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CAT_NAME + " TEXT, "
                + COL_CAT_TYPE + " TEXT)")

        val createTransactionsTable = ("CREATE TABLE " + TABLE_TRANSACTIONS + " ("
                + COL_TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TRANS_TITLE + " TEXT, "
                + COL_TRANS_AMOUNT + " REAL, "
                + COL_TRANS_TYPE + " TEXT, "
                + COL_TRANS_DATE + " TEXT, "
                + COL_TRANS_CATEGORY + " TEXT)")

        db?.execSQL(createCategoriesTable)
        db?.execSQL(createTransactionsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        onCreate(db)
    }

    // --- CÁC HÀM THAO TÁC CƠ SỞ DỮ LIỆU BÀI 6 ---

    // 1. Thêm giao dịch mới
    fun insertTransaction(title: String, amount: Double, type: String, date: String, category: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_TRANS_TITLE, title)
            put(COL_TRANS_AMOUNT, amount)
            put(COL_TRANS_TYPE, type)
            put(COL_TRANS_DATE, date)
            put(COL_TRANS_CATEGORY, category)
        }
        val result = db.insert(TABLE_TRANSACTIONS, null, values)
        db.close()
        return result
    }

    // 2. Cập nhật / Sửa giao dịch
    fun updateTransaction(id: Int, title: String, amount: Double, type: String, date: String, category: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_TRANS_TITLE, title)
            put(COL_TRANS_AMOUNT, amount)
            put(COL_TRANS_TYPE, type)
            put(COL_TRANS_DATE, date)
            put(COL_TRANS_CATEGORY, category)
        }
        val result = db.update(TABLE_TRANSACTIONS, values, "$COL_TRANS_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    // 3. Xóa giao dịch
    fun deleteTransaction(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_TRANSACTIONS, "$COL_TRANS_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    // 4. Lấy tất cả danh sách giao dịch
    fun getAllTransactions(): List<TransactionItem> {
        val list = mutableListOf<TransactionItem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TRANSACTIONS ORDER BY $COL_TRANS_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANS_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANS_TITLE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRANS_AMOUNT))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANS_TYPE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANS_DATE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANS_CATEGORY))
                list.add(TransactionItem(id, title, amount, type, date, category))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    // 5. Tính tổng thu nhập
    fun getTotalIncome(): Double {
        var total = 0.0
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COL_TRANS_AMOUNT) FROM $TABLE_TRANSACTIONS WHERE $COL_TRANS_TYPE = 'Income'", null)
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return total
    }

    // 6. Tính tổng chi tiêu
    fun getTotalExpense(): Double {
        var total = 0.0
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COL_TRANS_AMOUNT) FROM $TABLE_TRANSACTIONS WHERE $COL_TRANS_TYPE = 'Expense'", null)
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return total
    }
}