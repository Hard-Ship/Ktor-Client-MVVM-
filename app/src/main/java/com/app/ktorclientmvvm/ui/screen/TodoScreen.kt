package com.app.ktorclientmvvm.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.ktorclientmvvm.core.TodoScreenUiState
import com.app.ktorclientmvvm.core.TodoViewModel
import com.app.ktorclientmvvm.ui.component.ErrorStateUi
import com.app.ktorclientmvvm.ui.component.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column(modifier.padding(8.dp)) {
                    Text(
                        "Todo",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "A Ktor Client App",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

            })
        },
        floatingActionButton = {
            if (uiState is TodoScreenUiState.Success) {
                ExtendedFloatingActionButton(onClick = {
                    viewModel.onAddTodo()
                }) {
                    Text(" +  Add")
                }
            }
        }
    )
    { innerPadding ->

        when (uiState) {
            is TodoScreenUiState.Error -> {
                val data = uiState as TodoScreenUiState.Error
                ErrorStateUi(
                    message = data.message, modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            TodoScreenUiState.Loading -> {
                Box(
                    modifier
                        .fillMaxSize()
                        .padding(innerPadding), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is TodoScreenUiState.Success -> {
                val data = uiState as TodoScreenUiState.Success
                LazyColumn(contentPadding = innerPadding) {
                    items(data.todoList) { todo->
                        TodoItem(
                            todo,
                            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            onToggle = {
                                viewModel.onToggleCompleted(it , todo)
                            }
                        )
                    }
                }
            }
        }
    }
}