package com.example.submissioncompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PetRepository
import com.example.submissioncompose.model.Pet
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: PetRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Pet>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Pet>>
        get() = _uiState

    fun getPetById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getPetById(id))
    }


    fun updatePet(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updatePet(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getPetById(id)
            }
    }

}