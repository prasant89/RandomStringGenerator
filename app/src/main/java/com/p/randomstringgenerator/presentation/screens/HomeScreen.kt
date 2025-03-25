package com.p.randomstringgenerator.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.p.randomstringgenerator.presentation.viewmodel.RandomStringViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: RandomStringViewModel
) {
    var length by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("String Generator") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            label = { Text("Length") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = length,
                            onValueChange = {
                                length = it
                            }
                        )

                        Button(
                            modifier = Modifier.padding(start = 8.dp),
                            onClick = {
                                val number = length.toIntOrNull()
                                if (number == null || number <= 0) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a valid number greater than 0",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.generateString(length)

                                }
                            }
                        ) {
                            Text("Generate")
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (uiState.generatedStrings.isEmpty()) {
                            NoItemsData()
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                items(uiState.generatedStrings.size) { index ->
                                    val itemText = uiState.generatedStrings.getOrNull(index)
                                    if (itemText != null) {
                                        ListItemComponent(
                                            text = itemText.value,
                                            onDelete = {
                                                viewModel.deleteString(uiState.generatedStrings[index])
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    ClearAllButton(onClick = {
                        viewModel.deleteAllStrings()
                    })
                }
            }

        }
    }
}


@Composable
fun NoItemsData() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No items to display")
    }
}

@Composable
fun ListItemComponent(text: String, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun ClearAllButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = onClick) {
            Text("Clear All")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        modifier = Modifier,
        viewModel = hiltViewModel()
    )
}