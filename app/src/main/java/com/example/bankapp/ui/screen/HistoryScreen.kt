package com.example.bankapp.ui.screen

import com.example.bankapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.data.dummy.DummyData
import com.example.bankapp.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit
) {
    val transactions = DummyData.transactions
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Transfer",)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape).background(PrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "MKP", color = OnPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "MKP BANK", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Primary)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Primary)
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Primary)
                    }
                    Box(
                        modifier = Modifier.size(32.dp).clip(CircleShape).background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "SY", color = Primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background.copy(alpha = 0.85f)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Background)
        ) {
            // Header Section
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = "Transaction History",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
                Text(
                    text = "Manage and track your recent financial activities",
                    fontSize = 14.sp,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Filter Chips - Horizontal Scroll
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        label = filter,
                        isSelected = selectedFilter == filter,
                        onClick = { selectedFilter = filter }
                    )
                }
            }

            // Transaction List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val filteredTransactions = if (selectedFilter == "All") {
                    transactions
                } else {
                    transactions.filter { it.description.contains(selectedFilter, ignoreCase = true) }
                }

                if (filteredTransactions.isEmpty()) {
                    item {
                        EmptyStateView()
                    }
                } else {
                    items(filteredTransactions) { transaction ->
                        TransactionCard(transaction = transaction)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) Primary else SurfaceContainerLow,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isSelected) OnPrimary else OnSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun TransactionCard(transaction: Transaction) {
    val isSuccess = transaction.status == "Success"
    val isCredit = transaction.amount > 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Icon + Info
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Box
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSuccess) {
                                if (isCredit) SecondaryFixed else PrimaryContainer
                            } else {
                                SurfaceContainerHigh
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isCredit) Icons.Default.ArrowDownward else Icons.Default.Payments,
                        contentDescription = null,
                        tint = if (isSuccess) {
                            if (isCredit) OnSecondaryFixedVariant else OnPrimary
                        } else {
                            Outline
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Transaction Info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = transaction.description,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = transaction.date,
                        fontSize = 12.sp,
                        color = OnSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            // Right: Amount + Status
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "${if (isCredit) "+" else "-"} Rp ${"%,.0f".format(kotlin.math.abs(transaction.amount))}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCredit) Secondary else Primary
                )

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSuccess) {
                        SecondaryFixed.copy(alpha = 0.3f)
                    } else {
                        ErrorContainer.copy(alpha = 0.5f)
                    }
                ) {
                    Text(
                        text = transaction.status,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSuccess) OnSecondaryFixedVariant else Error,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStateView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(PrimaryFixed.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = Primary.copy(alpha = 0.5f),
                modifier = Modifier.size(48.dp)
            )
        }

        Text(
            text = "No transactions yet",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Primary
        )

        Text(
            text = "Your transaction history is empty. Start using your MKP Bank account to see records here.",
            fontSize = 13.sp,
            color = OnSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Button(
            onClick = { },
            modifier = Modifier.padding(top = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text(text = "Make a Transaction", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun TransactionShimmer() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerHigh)
                )
                Column {
                    Box(modifier = Modifier.size(120.dp, 16.dp).background(SurfaceContainerHigh).clip(RoundedCornerShape(4.dp)))
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.size(80.dp, 12.dp).background(SurfaceContainer).clip(RoundedCornerShape(4.dp)))
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Box(modifier = Modifier.size(80.dp, 16.dp).background(SurfaceContainerHigh).clip(RoundedCornerShape(4.dp)))
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.size(40.dp, 12.dp).background(SurfaceContainer).clip(RoundedCornerShape(4.dp)))
            }
        }
    }
}