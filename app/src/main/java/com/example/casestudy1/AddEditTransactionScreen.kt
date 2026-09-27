package com.example.casestudy1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTransactionScreen(
    screenTitle: String = "Thêm giao dịch",
    initialAmount: String = "",
    initialNote: String = ""
) {
    var isExpense by remember { mutableStateOf(true) }
    var category by remember { mutableStateOf("Ăn uống") }
    var amount by remember { mutableStateOf(initialAmount) }
    var date by remember { mutableStateOf("12/04/2025") }
    var note by remember { mutableStateOf(initialNote) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Header Toolbar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = screenTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        // 2. Nút chuyển Chi tiêu / Thu nhập
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isExpense = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExpense) Color(0xFFFF5252) else Color.LightGray
                )
            ) {
                Text("Chi tiêu", color = Color.White)
            }
            Button(
                onClick = { isExpense = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isExpense) Color(0xFF4CAF50) else Color.LightGray
                )
            ) {
                Text("Thu nhập", color = Color.White)
            }
        }

        // 3. Ô Danh mục
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Danh mục") },
            modifier = Modifier.fillMaxWidth()
        )

        // 4. Ô Số tiền
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Số tiền") },
            placeholder = { Text("Nhập số tiền") },
            suffix = { Text("đ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // 5. Ô Ngày giao dịch
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Ngày giao dịch") },
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar") },
            modifier = Modifier.fillMaxWidth()
        )

        // 6. Ô Ghi chú
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Ghi chú") },
            placeholder = { Text("Nhập ghi chú (tùy chọn)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // 7. Nút Lưu
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
        ) {
            Text("Lưu", fontSize = 16.sp, color = Color.White)
        }
    }
}

// Màn hình 1: THÊM GIAO DỊCH
@Preview(showBackground = true, name = "1. Thêm giao dịch")
@Composable
fun PreviewAddTransactionScreen() {
    AddEditTransactionScreen(
        screenTitle = "Thêm giao dịch",
        initialAmount = "",
        initialNote = ""
    )
}

// Màn hình 2: SỬA GIAO DỊCH
@Preview(showBackground = true, name = "2. Sửa giao dịch")
@Composable
fun PreviewEditTransactionScreen() {
    AddEditTransactionScreen(
        screenTitle = "Sửa giao dịch",
        initialAmount = "100.000",
        initialNote = "Ăn trưa"
    )
}