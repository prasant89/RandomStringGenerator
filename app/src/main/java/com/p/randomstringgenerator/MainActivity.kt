package com.p.randomstringgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.p.randomstringgenerator.presentation.screens.HomeScreen
import com.p.randomstringgenerator.presentation.viewmodel.RandomStringViewModel
import com.p.randomstringgenerator.ui.theme.RandomStringGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var randomStringViewModel: RandomStringViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        randomStringViewModel = ViewModelProvider(this).get(RandomStringViewModel::class.java)
        setContent {
            RandomStringGeneratorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        randomStringViewModel
                    )
                }
            }
        }
    }
}
