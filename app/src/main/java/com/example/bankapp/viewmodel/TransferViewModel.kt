package com.example.bankapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.dummy.DummyData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TransferUiState(
    val balance: Double = DummyData.dummyUser.balance,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class TransferViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TransferUiState())
    val uiState: StateFlow<TransferUiState> = _uiState.asStateFlow()

    fun transfer(accountNumber: String, amount: Double) {
        when {
            accountNumber.isBlank() -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "No. rekening tidak boleh kosong"
                )
                return
            }
            accountNumber.length < 10 -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Nomor rekening minimal 10 digit"
                )
                return
            }
            amount <= 0 -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Jumlah transfer harus lebih dari 0"
                )
                return
            }
            amount > _uiState.value.balance -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Saldo tidak mencukupi"
                )
                return
            }
            else -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        errorMessage = null
                    )


                    delay(1500)

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        balance = _uiState.value.balance - amount
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = TransferUiState()
    }
}