package com.rudra.moneynest.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
import com.rudra.moneynest.data.local.db.entity.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    init {
        repository.getAllCategories()
            .onEach { _categories.value = it }
            .launchIn(viewModelScope)
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }
    fun updateCategory(category: Category) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

}
