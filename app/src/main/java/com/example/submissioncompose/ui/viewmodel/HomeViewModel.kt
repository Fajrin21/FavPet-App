package com.example.submissioncompose.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.PetRepository
import com.example.submissioncompose.model.Pet
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PetRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Pet>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Pet>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchPet(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatePet(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updatePet(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}