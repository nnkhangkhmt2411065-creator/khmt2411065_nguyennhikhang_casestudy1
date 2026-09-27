package com.example.casestudy1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Màn hình 1: THÊM GIAO DỊCH
            AddEditTransactionScreen(
                screenTitle = "Thêm giao dịch",
                initialAmount = "",
                initialNote = ""
            )

            // Màn hình 2: SỬA GIAO DỊCH (Muốn hiển thị màn này thì ẩn dòng trên, bật dòng dưới):
            /*
            AddEditTransactionScreen(
                screenTitle = "Sửa giao dịch",
                initialAmount = "100.000",
                initialNote = "Ăn trưa"
            )
            */
        }
    }
}