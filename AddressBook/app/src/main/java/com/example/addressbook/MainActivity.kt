package com.example.addressbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.addressbook.ui.MainApp
import com.example.addressbook.ui.MainScreen
import com.example.addressbook.ui.MainViewModel
import com.example.addressbook.ui.theme.AddressBookTheme
import com.example.addressbook.util.ViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                val viewModel: MainViewModel = viewModel(
                    factory = ViewModelFactory(application)
                )
                MainApp(viewModel = viewModel)
            }
        }
    }
}