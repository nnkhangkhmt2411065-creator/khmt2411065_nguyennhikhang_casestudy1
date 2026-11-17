package com.example.casestudy1

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTransactionScreen(
    transactionToEdit: TransactionItem? = null,
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }
    val isEditMode = transactionToEdit != null

    val initialDate = remember {
        transactionToEdit?.date ?: SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }

    var selectedType by remember { mutableStateOf(transactionToEdit?.type ?: "Expense") }
    var category by remember { mutableStateOf(transactionToEdit?.category ?: "Ăn uống") }
    var amount by remember {
        mutableStateOf(
            transactionToEdit?.amount?.let { String.format(Locale.US, "%.0f", it) } ?: ""
        )
    }
    var date by remember { mutableStateOf(initialDate) }
    var note by remember { mutableStateOf(transactionToEdit?.title ?: "") }
    var expandedCategory by remember { mutableStateOf(false) }

    val categories = if (selectedType == "Expense") {
        listOf("Ăn uống", "Di chuyển", "Mua sắm", "Giáo dục", "Giải trí", "Hóa đơn")
    } else {
        listOf("Thu nhập", "Lương", "Thưởng", "Đầu tư", "Khác")
    }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Color.White,
        unfocusedBorderColor = Color(0xFFD1D5DB),
        focusedBorderColor = Color(0xFF1565D8),
        disabledBorderColor = Color(0xFFD1D5DB),
        disabledTextColor = Color(0xFF111827)
    )

    Scaffold(
        containerColor = Color(0xFFF6F8FB),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF6F8FB)
                ),
                title = {
                    Text(
                        text = if (isEditMode) "Sửa giao dịch" else "Thêm giao dịch",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF111827)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = Color(0xFF111827)
                        )
                    }
                },
                actions = {
                    if (isEditMode && transactionToEdit != null) {
                        IconButton(
                            onClick = {
                                dbHelper.deleteTransaction(transactionToEdit.id)
                                Toast.makeText(context, "Đã xóa giao dịch!", Toast.LENGTH_SHORT).show()
                                onNavigateBack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Xóa",
                                tint = Color(0xFFDC2626)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val isExpense = selectedType == "Expense"
                Button(
                    onClick = {
                        selectedType = "Expense"
                        category = "Ăn uống"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = if (isExpense) null else BorderStroke(1.dp, Color(0xFFD1D5DB)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isExpense) Color(0xFFF85C64) else Color.White,
                        contentColor = if (isExpense) Color.White else Color(0xFF111827)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Chi tiêu",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Button(
                    onClick = {
                        selectedType = "Income"
                        category = "Thu nhập"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = if (!isExpense) null else BorderStroke(1.dp, Color(0xFFD1D5DB)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isExpense) Color(0xFFF85C64) else Color.White,
                        contentColor = if (!isExpense) Color.White else Color(0xFF111827)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Thu nhập",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Danh mục",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF111827)
                )

                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = !expandedCategory }
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(10.dp),
                        colors = fieldColors,
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF47279)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Restaurant,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color(0xFF4B5563)
                            )
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false },
                        containerColor = Color.White
                    ) {
                        categories.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    category = item
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Số tiền",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF111827)
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    placeholder = {
                        Text(
                            text = "Nhập số tiền",
                            color = Color(0xFF6B7280),
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = fieldColors,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    trailingIcon = {
                        Text(
                            text = "đ",
                            color = Color(0xFF4B5563),
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Ngày giao dịch",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF111827)
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val cal = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _, y, m, d ->
                                    date = String.format(Locale.getDefault(), "%02d/%02d/%04d", d, m + 1, y)
                                },
                                cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        },
                    shape = RoundedCornerShape(10.dp),
                    colors = fieldColors,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Chọn ngày",
                            tint = Color(0xFF6B7280),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Ghi chú",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF111827)
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = {
                        Text(
                            text = "Nhập ghi chú (tùy chọn)",
                            color = Color(0xFF6B7280),
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = fieldColors
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val amountValue = amount.replace(".", "").replace(",", "").toDoubleOrNull()
                    if (amountValue == null || amountValue <= 0) {
                        Toast.makeText(context, "Vui lòng nhập số tiền hợp lệ!", Toast.LENGTH_SHORT).show()
                    } else {
                        val transactionTitle = note.trim().ifEmpty { category }
                        if (isEditMode && transactionToEdit != null) {
                            val rows = dbHelper.updateTransaction(
                                id = transactionToEdit.id,
                                title = transactionTitle,
                                amount = amountValue,
                                type = selectedType,
                                date = date,
                                category = category
                            )
                            if (rows > 0) {
                                Toast.makeText(context, "Cập nhật giao dịch thành công!", Toast.LENGTH_SHORT).show()
                                onNavigateBack()
                            } else {
                                Toast.makeText(context, "Lỗi khi cập nhật!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val result = dbHelper.insertTransaction(
                                title = transactionTitle,
                                amount = amountValue,
                                type = selectedType,
                                date = date,
                                category = category
                            )
                            if (result != -1L) {
                                Toast.makeText(context, "Lưu giao dịch thành công!", Toast.LENGTH_SHORT).show()
                                onNavigateBack()
                            } else {
                                Toast.makeText(context, "Lỗi khi lưu vào Database!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565D8),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Lưu",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}