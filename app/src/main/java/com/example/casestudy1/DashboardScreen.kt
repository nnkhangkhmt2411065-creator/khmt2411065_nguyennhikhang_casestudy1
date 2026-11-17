package com.example.casestudy1

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAddTransaction: () -> Unit = {},
    onNavigateToEditTransaction: (TransactionItem) -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    var transactionList by remember { mutableStateOf(listOf<TransactionItem>()) }
    var totalIncome by remember { mutableStateOf(0.0) }
    var totalExpense by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        transactionList = dbHelper.getAllTransactions()
        totalIncome = dbHelper.getTotalIncome()
        totalExpense = dbHelper.getTotalExpense()
    }

    val currentBalance = totalIncome - totalExpense

    Scaffold(
        containerColor = Color(0xFFF6F8FB),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF6F8FB)
                ),
                title = {
                    Text(
                        text = "Quản lý thu chi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF111827)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color(0xFF111827)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Color(0xFFEF4444),
                                    contentColor = Color.White
                                ) {
                                    Text("3", fontSize = 10.sp)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Thông báo",
                                tint = Color(0xFF111827)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTransaction,
                containerColor = Color(0xFF1A68D1),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Thêm giao dịch",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Trang chủ", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                    selected = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1A68D1),
                        selectedTextColor = Color(0xFF1A68D1),
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color(0xFF4B5563),
                        unselectedTextColor = Color(0xFF4B5563)
                    ),
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Article, contentDescription = null) },
                    label = { Text("Giao dịch", fontSize = 11.sp) },
                    selected = false,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color(0xFF4B5563),
                        unselectedTextColor = Color(0xFF4B5563)
                    ),
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PieChart, contentDescription = null) },
                    label = { Text("Thống kê", fontSize = 11.sp) },
                    selected = false,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Color(0xFF4B5563),
                        unselectedTextColor = Color(0xFF4B5563)
                    ),
                    onClick = {}
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF2B7CE9),
                                Color(0xFF155BC0)
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "SỐ DƯ HIỆN TẠI",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "Ẩn/Hiện",
                                    tint = Color.White.copy(alpha = 0.9f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = formatMoney(currentBalance),
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "đ",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Wallet3DVector()
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.White)
                        )
                        repeat(3) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.45f))
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    modifier = Modifier.weight(1f),
                    title = "TỔNG THU NHẬP",
                    amount = totalIncome,
                    background = Color(0xFFECF9F0),
                    ringColor = Color(0xFF66BB6A),
                    textColor = Color(0xFF2E7D32),
                    isIncome = true
                )

                SummaryCard(
                    modifier = Modifier.weight(1f),
                    title = "TỔNG CHI TIÊU",
                    amount = totalExpense,
                    background = Color(0xFFFDF0F0),
                    ringColor = Color(0xFFEF5350),
                    textColor = Color(0xFFC62828),
                    isIncome = false
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Giao dịch gần đây",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF111827)
                )
                Text(
                    text = "Xem tất cả",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A68D1)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (transactionList.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Chưa có giao dịch nào. Bấm (+) để thêm!",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        itemsIndexed(transactionList) { index, item ->
                            val isIncome = item.type == "Income"

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigateToEditTransaction(item) }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CategoryCircleCanvas(
                                    title = item.title,
                                    category = item.category
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF111827)
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = item.category,
                                            fontSize = 11.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = item.date,
                                            fontSize = 11.sp,
                                            color = Color(0xFF6B7280)
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "${if (isIncome) "+" else "-"}${formatMoney(item.amount)} ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFDC2626)
                                    )
                                    Text(
                                        text = "đ",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFDC2626),
                                        textDecoration = TextDecoration.Underline
                                    )
                                }
                            }

                            if (index < transactionList.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 14.dp),
                                    thickness = 0.6.dp,
                                    color = Color(0xFFF3F4F6)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(
    modifier: Modifier,
    title: String,
    amount: Double,
    background: Color,
    ringColor: Color,
    textColor: Color,
    isIncome: Boolean
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = background),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(ringColor),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    ArrowDrawIcon(isDown = isIncome, color = textColor)
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 9.sp,
                    color = Color(0xFF4B5563),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${formatMoney(amount)} ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Text(
                        text = "đ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

private fun formatMoney(value: Double): String {
    return String.format("%,.0f", value).replace(",", ".")
}

@Composable
fun Wallet3DVector() {
    Canvas(modifier = Modifier.size(64.dp)) {
        val w = size.width
        val h = size.height

        drawRoundRect(
            color = Color(0xFF66BB6A),
            topLeft = Offset(w * 0.25f, h * 0.08f),
            size = Size(w * 0.55f, h * 0.35f),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
        )
        drawRoundRect(
            color = Color(0xFF81C784),
            topLeft = Offset(w * 0.32f, h * 0.14f),
            size = Size(w * 0.52f, h * 0.32f),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
        )
        drawRoundRect(
            color = Color(0xFF15458C),
            topLeft = Offset(w * 0.15f, h * 0.28f),
            size = Size(w * 0.72f, h * 0.52f),
            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
        )
        drawRoundRect(
            color = Color(0xFF0D2F66),
            topLeft = Offset(w * 0.65f, h * 0.44f),
            size = Size(w * 0.25f, h * 0.20f),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
        )
        drawCircle(
            color = Color(0xFF90CAF9),
            radius = 3.dp.toPx(),
            center = Offset(w * 0.74f, h * 0.54f)
        )
        drawCircle(
            color = Color(0xFFFFC107),
            radius = 9.dp.toPx(),
            center = Offset(w * 0.22f, h * 0.76f)
        )
        drawCircle(
            color = Color(0xFFFFD54F),
            radius = 6.dp.toPx(),
            center = Offset(w * 0.22f, h * 0.76f)
        )
    }
}

@Composable
fun CategoryCircleCanvas(
    title: String,
    category: String
) {
    val backgroundColor = when {
        title.contains("Ăn", ignoreCase = true) || category.contains("Ăn", ignoreCase = true) -> Color(0xFFF97316)
        title.contains("Xăng", ignoreCase = true) || category.contains("Di chuyển", ignoreCase = true) -> Color(0xFF0EA5E9)
        title.contains("Lương", ignoreCase = true) || category.contains("Thu", ignoreCase = true) -> Color(0xFF16A34A)
        title.contains("Mua", ignoreCase = true) || category.contains("Sắm", ignoreCase = true) -> Color(0xFF9333EA)
        title.contains("Học", ignoreCase = true) || category.contains("Giáo dục", ignoreCase = true) -> Color(0xFF0D9488)
        else -> Color(0xFF0D9488)
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(18.dp)) {
            val w = size.width
            val h = size.height
            val stroke = Stroke(width = 2.dp.toPx())

            when {
                title.contains("Ăn", ignoreCase = true) || category.contains("Ăn", ignoreCase = true) -> {
                    drawLine(Color.White, Offset(w * 0.3f, 0f), Offset(w * 0.3f, h), strokeWidth = 2.dp.toPx())
                    drawLine(Color.White, Offset(w * 0.7f, 0f), Offset(w * 0.7f, h), strokeWidth = 2.dp.toPx())
                }
                title.contains("Xăng", ignoreCase = true) || category.contains("Di chuyển", ignoreCase = true) -> {
                    drawRoundRect(Color.White, Offset(w * 0.1f, h * 0.3f), Size(w * 0.8f, h * 0.45f), CornerRadius(3.dp.toPx()), style = stroke)
                }
                title.contains("Lương", ignoreCase = true) || category.contains("Thu", ignoreCase = true) -> {
                    drawCircle(color = Color.White, radius = w * 0.38f, style = stroke)
                }
                title.contains("Mua", ignoreCase = true) || category.contains("Sắm", ignoreCase = true) -> {
                    val path = Path().apply {
                        moveTo(w * 0.2f, h * 0.85f)
                        lineTo(w * 0.8f, h * 0.85f)
                        lineTo(w * 0.95f, h * 0.3f)
                        lineTo(w * 0.05f, h * 0.3f)
                        close()
                    }
                    drawPath(path = path, color = Color.White, style = stroke)
                }
                else -> {
                    drawCircle(color = Color.White, radius = w * 0.35f, style = stroke)
                }
            }
        }
    }
}

@Composable
fun ArrowDrawIcon(isDown: Boolean, color: Color) {
    Canvas(modifier = Modifier.size(11.dp)) {
        val w = size.width
        val h = size.height
        val strokeWidth = 2.dp.toPx()

        if (isDown) {
            drawLine(color, Offset(w / 2, 0f), Offset(w / 2, h), strokeWidth = strokeWidth)
            drawLine(color, Offset(w * 0.15f, h * 0.55f), Offset(w / 2, h), strokeWidth = strokeWidth)
            drawLine(color, Offset(w * 0.85f, h * 0.55f), Offset(w / 2, h), strokeWidth = strokeWidth)
        } else {
            drawLine(color, Offset(w / 2, h), Offset(w / 2, 0f), strokeWidth = strokeWidth)
            drawLine(color, Offset(w * 0.15f, h * 0.45f), Offset(w / 2, 0f), strokeWidth = strokeWidth)
            drawLine(color, Offset(w * 0.85f, h * 0.45f), Offset(w / 2, 0f), strokeWidth = strokeWidth)
        }
    }
}