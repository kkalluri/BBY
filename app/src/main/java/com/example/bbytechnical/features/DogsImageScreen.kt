package com.example.bbytechnical.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bbytechnical.data.remote.NetworkResult


/**
 *
 * BBYTechnical
 * Created by venkatakalluri on 11/21/23
 */


@Composable
fun ListBasedOnPickerComposable(viewModel: DogBreedViewModel, fetchDataBasedOnPickerSelection: (String) -> Unit){

    val dogImageResult by viewModel.dogImages.collectAsState()
    val selectedItem by viewModel.selectedItem.observeAsState()
    // Trigger data fetching when the selected item changes
    LaunchedEffect(selectedItem) {
        println("LaunchedEffect triggered with value: ${viewModel.selectedItem.value}")
        viewModel.selectedItem.value?.let { selectedItem ->
            fetchDataBasedOnPickerSelection(selectedItem)
        }
    }

    when (dogImageResult) {
        is NetworkResult.Loading -> {
            LoadingBar()
        }

        is NetworkResult.Success -> {
            var allImages = (dogImageResult as NetworkResult.Success<List<String>>).data.toMutableList()
            Column(
                Modifier
                    .padding(20.dp)
            ) {
                DogImagesScreen(allImages)
            }
        }

        is NetworkResult.Failure -> {
            var errorMessage =
                (dogImageResult as NetworkResult.Failure<List<String>>).errorMessage
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(10.dp))
        }
    }

}

@Composable
fun DogImagesScreen(dogImages: List<String>) {

    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(dogImages){
            DogImageItem(dogImageUrl = it, Modifier)
        }
    }

}

@Composable
fun DogImageItem(dogImageUrl: String, modifier: Modifier) {

    Card(modifier = Modifier.padding(5.dp)) {
        AsyncImage(
            model = dogImageUrl,
            contentDescription = null,
            modifier = modifier.fillMaxWidth()
                .height(200.dp)
                .clip(RectangleShape)


        )
    }

}

@Preview
@Composable
private fun DogImagePreview(){
    DogImageItem("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg", Modifier)
}

@Preview
@Composable
fun DogImagesListPreview() {

    val dogsList = mutableListOf<String>("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
        "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg")
    DogImagesScreen(dogImages = dogsList)

}
