package com.example.bbytechnical.features

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


/**
 *
 * BBYTechnical
 * Created by venkatakalluri on 11/21/23
 */

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
