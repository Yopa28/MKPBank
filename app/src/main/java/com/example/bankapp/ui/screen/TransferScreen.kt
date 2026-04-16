package com.example.bankapp.ui.screen

import com.example.bankapp.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bankapp.viewmodel.TransferViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    viewModel: TransferViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            showSuccessDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Transfer", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = OnPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = OnPrimary,
                    navigationIconContentColor = OnPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Column {
                Text(text = "Move Funds", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
                Text(text = "Transaction", fontSize = 10.sp, color = OnSurfaceVariant, fontWeight = FontWeight.SemiBold)
            }

            // Source Account Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(PrimaryGradientStart, PrimaryGradientEnd),
                                start = Offset(0f, 0f),
                                end = Offset(1f, 1f)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Text(text = "From Primary Savings", fontSize = 11.sp, color = OnPrimary.copy(alpha = 0.7f), fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Rp ${"%,.0f".format(uiState.balance)}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "•••• 8821", fontSize = 11.sp, color = OnPrimary.copy(alpha = 0.8f))
                            Box(modifier = Modifier.size(4.dp).clip(RoundedCornerShape(2.dp)).background(OnPrimary.copy(alpha = 0.5f)))
                            Text(text = "Sandy Yopa", fontSize = 11.sp, color = OnPrimary.copy(alpha = 0.8f))
                        }
                    }
                }
            }

            // Input Fields
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Account Number
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Destination Account Number", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceVariant)
                    OutlinedTextField(
                        value = accountNumber,
                        onValueChange = { accountNumber = it },
                        placeholder = { Text("Min. 10 digits", color = Outline) },
                        leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Primary) },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceContainerHigh,
                            focusedLeadingIconColor = Primary,
                            unfocusedLeadingIconColor = Primary
                        )
                    )
                }

                // Amount
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Transfer Amount", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceVariant)
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("0", color = Outline) },
                        leadingIcon = { Text(text = "Rp", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Primary) },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceContainerHigh,
                            focusedLeadingIconColor = Primary,
                            unfocusedLeadingIconColor = Primary
                        )
                    )
                }

                // Error Message
                if (uiState.errorMessage != null) {
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = ErrorContainer), shape = RoundedCornerShape(12.dp)) {
                        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Error, contentDescription = null, tint = Error, modifier = Modifier.size(20.dp))
                            Text(text = uiState.errorMessage!!, fontSize = 12.sp, color = Error, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            // Transfer Button
            Button(
                onClick = {
                    val amountValue = amount.toDoubleOrNull() ?: 0.0
                    if (accountNumber.length >= 10 && amountValue > 0 && amountValue <= uiState.balance) {
                        showConfirmDialog = true
                    } else {
                        viewModel.transfer(accountNumber, amountValue)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                enabled = !uiState.isLoading && accountNumber.isNotBlank() && amount.isNotBlank()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = OnPrimary, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text(text = "Send Transfer", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Recent Transfers
            Column {
                Text(text = "Recent Transfers", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    RecentTransferItem(
                        name = "Sandy",
                        account = "BCA •••• 1234",
                        containerColor = SecondaryFixedDim,
                        iconColor = OnSecondaryFixedVariant,
                        modifier = Modifier.weight(1f)
                    )
                    RecentTransferItem(
                        name = "Yopa",
                        account = "BRI •••• 4321",
                        containerColor = SecondaryFixedDim,
                        iconColor = OnSecondaryFixedVariant,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Confirmation Dialog
        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                icon = {
                    Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(SurfaceContainerLow), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Primary, modifier = Modifier.size(28.dp))
                    }
                },
                title = { Text(text = "Confirm Transfer", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Primary) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = "Please review the transaction details before confirming your transfer.", fontSize = 12.sp, color = OnSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow), shape = RoundedCornerShape(16.dp)) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Amount", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Outline)
                                    Text(text = "Rp ${"%,.0f".format(amount.toDoubleOrNull() ?: 0.0)}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Primary)
                                }
                                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Recipient", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Outline)
                                    Text(text = "****${accountNumber.takeLast(4)}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSurface)
                                }
                                HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Fee", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Outline)
                                    Text(text = "Free", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = OnSecondaryFixedVariant)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmDialog = false
                            viewModel.transfer(accountNumber, amount.toDoubleOrNull() ?: 0.0)
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) { Text(text = "Confirm", fontSize = 14.sp, fontWeight = FontWeight.SemiBold) }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showConfirmDialog = false },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceVariant)
                    ) { Text(text = "Cancel", fontSize = 14.sp, fontWeight = FontWeight.SemiBold) }
                },
                containerColor = SurfaceContainerLowest,
                shape = RoundedCornerShape(32.dp)
            )
        }

        // Success Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { },
                icon = {
                    Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(Secondary.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                        Text(text = "✅", fontSize = 32.sp)
                    }
                },
                title = { Text(text = "Transfer Berhasil!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Primary) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Transfer ke ${accountNumber}", fontSize = 14.sp, color = OnSurface)
                        Text(text = "sebesar Rp ${"%,.0f".format(amount.toDoubleOrNull() ?: 0.0)}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Primary)
                        Text(text = "berhasil dilakukan.", fontSize = 14.sp, color = OnSurface)
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            viewModel.resetState()
                            accountNumber = ""
                            amount = ""
                            onNavigateBack()
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) { Text(text = "Kembali ke Home", fontSize = 14.sp, fontWeight = FontWeight.SemiBold) }
                },
                containerColor = SurfaceContainerLowest,
                shape = RoundedCornerShape(32.dp)
            )
        }
    }
}

@Composable
fun RecentTransferItem(
    name: String,
    account: String,
    containerColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(containerColor),
                contentAlignment = Alignment.Center
            ) {

                Icon(Icons.Default.Person, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Column {
                Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = account, fontSize = 10.sp, color = Outline, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}