package com.example.bbytechnical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bbytechnical.features.DogBreedScreen
import com.example.bbytechnical.features.DogBreedViewModel
import com.example.bbytechnical.features.ListBasedOnPickerComposable
import com.example.bbytechnical.ui.theme.BBYTechnicalTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: DogBreedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBYTechnicalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        AppBar()
                        DogBreedScreen(viewModel = viewModel)
                        ListBasedOnPickerComposable(
                            viewModel = viewModel,
                            fetchDataBasedOnPickerSelection = { selectedItem ->
                                // Fetch data based on the selected item
                                // You can call your network request function or perform any other operation
                                // For example:
                                viewModel.fetchDogImages(selectedItem)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Set your desired background color
            titleContentColor = MaterialTheme.colorScheme.primary, // Set the text and icon color
        )
    )
}
