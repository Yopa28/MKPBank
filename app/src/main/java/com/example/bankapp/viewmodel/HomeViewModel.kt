package com.example.bankapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bankapp.data.dummy.DummyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val fullName: String = "",
    val username: String = "",
    val balance: Double = 0.0
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            fullName = DummyData.dummyUser.fullName,
            username = DummyData.dummyUser.username,
            balance = DummyData.dummyUser.balance
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun logout() {

        _uiState.value = HomeUiState()
    }
}