package com.example.bbytechnical.features

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bbytechnical.data.remote.NetworkResult
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.toSize
import com.example.bbytechnical.R

/**
 *
 * BBYTechnical
 * Created by venkatakalluri on 11/21/23.
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedScreen(viewModel: DogBreedViewModel) {
    val dogBreesResult by viewModel.breeds.collectAsState()
    val dogImagesResult by viewModel.dogImages.collectAsState()

    var allBreeds by remember { mutableStateOf<List<String>>(emptyList()) }
    var dogImages by remember { mutableStateOf<List<String>>(emptyList()) }
    var errorMessage: String? = null
    var imageErrorMessage: String? = null

    var isLoading by remember { mutableStateOf<Boolean>(false) }

    // Use LaunchedEffect to trigger the API call when the Composable is first displayed
    LaunchedEffect(true) {
        println("Fetching dog breeds...")
        viewModel.fetchDogBreeds()
    }



    when (dogBreesResult) {
        is NetworkResult.Loading -> {
            isLoading = !isLoading
        }

        is NetworkResult.Success -> {
            allBreeds = (dogBreesResult as NetworkResult.Success<List<String>>).data.toMutableList()
            isLoading = !isLoading
        }

        is NetworkResult.Failure -> {
            errorMessage =
                (dogBreesResult as NetworkResult.Failure<List<String>>).errorMessage
            isLoading = !isLoading
        }
    }

    when (dogImagesResult) {
        is NetworkResult.Loading -> {
            isLoading = !isLoading
        }

        is NetworkResult.Success -> {
            dogImages =
                (dogImagesResult as NetworkResult.Success<List<String>>).data.toMutableList()
            isLoading = !isLoading
        }

        is NetworkResult.Failure -> {
            imageErrorMessage =
                (dogImagesResult as NetworkResult.Failure<List<String>>).errorMessage
            isLoading = !isLoading
        }
    }

    Scaffold(
        topBar = { AppBar() },
        content = { it ->
            if (isLoading) {
                LoadingBar()
            } else {
                if (errorMessage == null) {
                    Column(
                        Modifier
                            .padding(20.dp)
                            .padding(it)
                    ) {
                        showBreedPicker(dogBreeds = allBreeds) {
                            viewModel.fetchDogImages(it)
                        }

                        if (imageErrorMessage == null) {
                            DogImagesScreen(dogImages)
                            if (dogImages.isEmpty()) {
                                Text(
                                    text = "No images found",
                                    color = Color.Red,
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                        } else {
                            // Display an error message
                            Text(text = imageErrorMessage, color = Color.Red)
                        }
                    }
                } else {
                    // Display an error message
                    Column(Modifier.padding(20.dp)) {
                        Text(text = errorMessage, color = Color.Red)
                    }
                }
            }


        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showBreedPicker(dogBreeds: List<String>, selectedDogBreed: (String) -> Unit) {
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }


    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {


        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            readOnly = true,
            label = { Text("Select Dog Breed") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .heightIn(max = 500.dp)
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            dogBreeds.forEach { label ->
                DropdownMenuItem(text = { Text(text = label) }, onClick = {
                    mSelectedText = label
                    mExpanded = false
                    selectedDogBreed(label)
                })
            }
        }
    }
}

@Preview
@Composable
fun showBreedPickerReview() {
    showBreedPicker(dogBreeds = mutableListOf("ABC", "DEF", "EFG"), selectedDogBreed = {})
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
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
