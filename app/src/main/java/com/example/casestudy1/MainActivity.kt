package com.example.casestudy1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showAddEditScreen by remember { mutableStateOf(false) }
            var editingTransaction by remember { mutableStateOf<TransactionItem?>(null) }

            if (showAddEditScreen) {
                AddEditTransactionScreen(
                    transactionToEdit = editingTransaction,
                    onNavigateBack = {
                        showAddEditScreen = false
                        editingTransaction = null
                    }
                )
            } else {
                DashboardScreen(
                    onNavigateToAddTransaction = {
                        editingTransaction = null
                        showAddEditScreen = true
                    },
                    onNavigateToEditTransaction = { item ->
                        editingTransaction = item
                        showAddEditScreen = true
                    }
                )
            }
        }
    }
}