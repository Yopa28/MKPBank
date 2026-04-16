package com.example.bankapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha 
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bankapp.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(120.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFD2E4FF).copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        center = Offset(1f, 0f)
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(120.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFDBE1FF).copy(alpha = 0.2f),
                            Color.Transparent
                        ),
                        center = Offset(0f, 1f)
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF000F22),
                            Color(0xFF0051D5),
                            Color(0xFF000F22)
                        )
                    )
                )
                .alpha(0.2f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF000F22), Color(0xFF0A2540))
                        ),
                        shape = RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MKP",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-1).sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "MKP Bank",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000F22),
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "Secure. Simple. Smart.",
                fontSize = 12.sp,
                color = Color(0xFF43474D).copy(alpha = 0.8f),
                letterSpacing = 2.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Username Field
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Username",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C1E),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            placeholder = {
                                Text("Enter your username", color = Color(0xFF74777E))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFF000F22)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF0051D5),
                                unfocusedBorderColor = Color(0xFFECEEF1),
                                focusedLeadingIconColor = Color(0xFF000F22),
                                unfocusedLeadingIconColor = Color(0xFF000F22)
                            ),
                            singleLine = true
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFF74777E).copy(alpha = 0.8f),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Cannot be empty",
                                fontSize = 11.sp,
                                color = Color(0xFF74777E).copy(alpha = 0.8f)
                            )
                        }
                    }

                    // Password Field
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Password",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C1E),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = {
                                Text("Enter your password", color = Color(0xFF74777E))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = Color(0xFF000F22)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = if (uiState.errorMessage != null)
                                            Color(0xFFBA1A1A)
                                        else
                                            Color(0xFF74777E)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (uiState.errorMessage != null)
                                    Color(0xFFBA1A1A)
                                else
                                    Color(0xFF0051D5),
                                unfocusedBorderColor = if (uiState.errorMessage != null)
                                    Color(0xFFBA1A1A).copy(alpha = 0.3f)
                                else
                                    Color(0xFFECEEF1),
                                focusedLeadingIconColor = Color(0xFF000F22),
                                unfocusedLeadingIconColor = Color(0xFF000F22),
                                focusedContainerColor = Color(0xFFFFDAD6).copy(alpha = 0.3f),
                                unfocusedContainerColor = if (uiState.errorMessage != null)
                                    Color(0xFFFFDAD6).copy(alpha = 0.3f)
                                else
                                    Color.White
                            ),
                            singleLine = true,
                            isError = uiState.errorMessage != null
                        )

                        if (uiState.errorMessage != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(start = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color(0xFFBA1A1A),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = uiState.errorMessage!!,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFBA1A1A)
                                )
                            }
                        }
                    }

                    // Login Button
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { viewModel.login(username, password) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0051D5)
                            ),
                            enabled = !uiState.isLoading
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Login",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        TextButton(
                            onClick = { },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "Forgot Password?",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0051D5)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {



                }
                Text(
                    text = "© 2026 MKP Bank International. All rights reserved.",
                    fontSize = 10.sp,
                    color = Color(0xFF74777E).copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                // Home Indicator
                Box(
                    modifier = Modifier
                        .width(132.dp)
                        .height(6.dp)
                        .background(
                            color = Color(0xFFE6E8EB),
                            shape = RoundedCornerShape(50)
                        )
                        .alpha(0.5f)
                )
            }
        }
    }
}