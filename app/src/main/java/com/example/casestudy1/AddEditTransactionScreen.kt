package com.example.casestudy1

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTransactionScreen(
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Expense") } // "Income" hoặc "Expense"
    var category by remember { mutableStateOf("Ăn uống") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thêm giao dịch", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Chọn Thu nhập / Chi tiêu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { selectedType = "Expense" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == "Expense") Color(0xFFC62828) else Color.LightGray
                    )
                ) {
                    Text("Chi tiêu", color = Color.White)
                }

                Button(
                    onClick = { selectedType = "Income" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == "Income") Color(0xFF2E7D32) else Color.LightGray
                    )
                ) {
                    Text("Thu nhập", color = Color.White)
                }
            }

            // Nhập tiêu đề
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tên giao dịch (VD: Ăn trưa, Lương...)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Nhập số tiền
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Số tiền (VNĐ)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Nhập danh mục
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Danh mục (VD: Ăn uống, Di chuyển, Mua sắm)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            // Nút Lưu giao dịch vào SQLite
            Button(
                onClick = {
                    val amountValue = amount.toDoubleOrNull()
                    if (title.isBlank() || amountValue == null || amountValue <= 0) {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ tên và số tiền hợp lệ!", Toast.LENGTH_SHORT).show()
                    } else {
                        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                        val result = dbHelper.insertTransaction(
                            title = title,
                            amount = amountValue,
                            type = selectedType,
                            date = currentDate,
                            category = category
                        )

                        if (result != -1L) {
                            Toast.makeText(context, "Lưu giao dịch thành công!", Toast.LENGTH_SHORT).show()
                            onNavigateBack()
                        } else {
                            Toast.makeText(context, "Lỗi khi lưu vào Database!", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("LƯU GIAO DỊCH", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
