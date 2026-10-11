package com.example.casestudy1

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAddTransaction: () -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    var transactionList by remember { mutableStateOf(listOf<TransactionItem>()) }
    var totalIncome by remember { mutableDoubleStateOf(0.0) }
    var totalExpense by remember { mutableDoubleStateOf(0.0) }

    LaunchedEffect(Unit) {
        transactionList = dbHelper.getAllTransactions()
        totalIncome = dbHelper.getTotalIncome()
        totalExpense = dbHelper.getTotalExpense()
    }

    val currentBalance = totalIncome - totalExpense

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quản lý thu chi", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        BadgedBox(badge = { Badge { Text("1") } }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Thông báo")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTransaction,
                containerColor = Color(0xFF2979FF),
                shape = CircleShape,
                modifier = Modifier.offset(y = (-10).dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            NavigationBar(containerColor = Color.White, elevation = 8.dp) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Trang chủ", fontSize = 10.sp) },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ListAlt, contentDescription = null) },
                    label = { Text("Giao dịch", fontSize = 10.sp) },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PieChart, contentDescription = null) },
                    label = { Text("Thống kê", fontSize = 10.sp) },
                    selected = false,
                    onClick = {}
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F7FA))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2979FF)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("SỐ DƯ HIỆN TẠI", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${String.format("%,.0f", currentBalance).replace(",", ".")} đ",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        WalletDrawIcon()
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF81C784)),
                            contentAlignment = Alignment.Center
                        ) {
                            ArrowDrawIcon(isDown = true)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("TỔNG THU NHẬP", fontSize = 8.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            Text(
                                text = "${String.format("%,.0f", totalIncome).replace(",", ".")} đ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xE5FF5252)),
                            contentAlignment = Alignment.Center
                        ) {
                            ArrowDrawIcon(isDown = false)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("TỔNG CHI TIÊU", fontSize = 8.sp, color = Color(0xFFC62828), fontWeight = FontWeight.Bold)
                            Text(
                                text = "${String.format("%,.0f", totalExpense).replace(",", ".")} đ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC62828)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Giao dịch gần đây", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Xem tất cả", fontSize = 11.sp, color = Color(0xFF2979FF))
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (transactionList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chưa có giao dịch nào. Bấm (+) để thêm!", color = Color.Gray, fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(transactionList) { item ->
                        val isIncome = item.type == "Income"
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(0.5.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CategoryCircleCanvas(title = item.title, category = item.category)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("${item.category}${item.date}", fontSize = 10.sp, color = Color.Gray)
                                }
                                Text(
                                    text = "${if (isIncome) "+" else "-"}${String.format("%,.0f", item.amount).replace(",", ".")} đ",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isIncome) Color(0xFF4CAF50) else Color(0xFFFF5252)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Canvas vẽ Icon đại diện tự động chuẩn 100%
@Composable
fun CategoryCircleCanvas(title: String, category: String) {
    val bgColor = when {
        title.contains("Ăn") || category.contains("Ăn") -> Color(0xFFFF6D00)
        title.contains("Xăng") || category.contains("Di chuyển") -> Color(0xFF00B0FF)
        title.contains("Lương") || category.contains("Thu") -> Color(0xFF00C853)
        title.contains("Mua") || category.contains("Sắm") -> Color(0xFFAA00FF)
        else -> Color(0xFF00B8D4)
    }

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(18.dp)) {
            val w = size.width
            val h = size.height
            val stroke = Stroke(width = 3.dp.toPx())

            if (title.contains("Ăn") || category.contains("Ăn")) {
                drawLine(Color.White, Offset(w * 0.3f, 0f), Offset(w * 0.3f, h), strokeWidth = 3.dp.toPx())
                drawLine(Color.White, Offset(w * 0.7f, 0f), Offset(w * 0.7f, h), strokeWidth = 3.dp.toPx())
            } else if (title.contains("Xăng") || category.contains("Di chuyển")) {
                drawCircle(Color.White, radius = w * 0.35f, style = stroke)
            } else if (title.contains("Lương") || category.contains("Thu")) {
                drawRect(Color.White, topLeft = Offset(0f, h * 0.2f), size = androidx.compose.ui.geometry.Size(w, h * 0.6f), style = stroke)
            } else if (title.contains("Mua") || category.contains("Sắm")) {
                drawPath(
                    path = Path().apply {
                        moveTo(w * 0.2f, h)
                        lineTo(w * 0.8f, h)
                        lineTo(w, h * 0.3f)
                        lineTo(0f, h * 0.3f)
                        close()
                    },
                    color = Color.White,
                    style = stroke
                )
            } else {
                drawRect(Color.White, style = stroke)
            }
        }
    }
}

@Composable
fun WalletDrawIcon() {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        drawRect(
            color = Color.White,
            topLeft = Offset(0f, h * 0.2f),
            size = androidx.compose.ui.geometry.Size(w, h * 0.6f),
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(Color.White, radius = 3.dp.toPx(), center = Offset(w * 0.75f, h * 0.5f))
    }
}

@Composable
fun ArrowDrawIcon(isDown: Boolean) {
    Canvas(modifier = Modifier.size(14.dp)) {
        val w = size.width
        val h = size.height
        val stroke = Stroke(width = 2.5.dp.toPx())
        if (isDown) {
            drawLine(Color.White, Offset(w / 2, 0f), Offset(w / 2, h), strokeWidth = 2.5.dp.toPx())
            drawLine(Color.White, Offset(w * 0.15f, h * 0.55f), Offset(w / 2, h), strokeWidth = 2.5.dp.toPx())
            drawLine(Color.White, Offset(w * 0.85f, h * 0.55f), Offset(w / 2, h), strokeWidth = 2.5.dp.toPx())
        } else {
            drawLine(Color.White, Offset(w / 2, h), Offset(w / 2, 0f), strokeWidth = 2.5.dp.toPx())
            drawLine(Color.White, Offset(w * 0.15f, h * 0.45f), Offset(w / 2, 0f), strokeWidth = 2.5.dp.toPx())
            drawLine(Color.White, Offset(w * 0.85f, h * 0.45f), Offset(w / 2, 0f), strokeWidth = 2.5.dp.toPx())
        }
    }
}

// --- PREVIEW HIỂN THỊ ICON CHUẨN ĐỀ BÀI ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreviewSample() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F7FA)
    ) {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text("Quản lý thu chi", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            BadgedBox(badge = { Badge { Text("1") } }) {
                                Icon(Icons.Default.Notifications, contentDescription = null)
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    containerColor = Color(0xFF2979FF),
                    shape = CircleShape,
                    modifier = Modifier.offset(y = (-10).dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Trang chủ", fontSize = 10.sp) },
                        selected = true,
                        onClick = {}
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ListAlt, contentDescription = null) },
                        label = { Text("Giao dịch", fontSize = 10.sp) },
                        selected = false,
                        onClick = {}
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.PieChart, contentDescription = null) },
                        label = { Text("Thống kê", fontSize = 10.sp) },
                        selected = false,
                        onClick = {}
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2979FF)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("SỐ DƯ HIỆN TẠI", fontSize = 11.sp, color = Color.White.copy(alpha = 0.8f))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("5.000.000 đ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            WalletDrawIcon()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF81C784)),
                                contentAlignment = Alignment.Center
                            ) {
                                ArrowDrawIcon(isDown = true)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("TỔNG THU NHẬP", fontSize = 8.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                                Text("8.000.000 đ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xE5FF5252)),
                                contentAlignment = Alignment.Center
                            ) {
                                ArrowDrawIcon(isDown = false)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("TỔNG CHI TIÊU", fontSize = 8.sp, color = Color(0xFFC62828), fontWeight = FontWeight.Bold)
                                Text("3.000.000 đ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC62828))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Giao dịch gần đây", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Xem tất cả", fontSize = 11.sp