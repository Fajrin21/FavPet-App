package com.example.submissioncompose.ui.screen

import com.example.submissioncompose.ui.viewmodel.DetailViewModel
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.R
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.viewmodel.ViewModelFactory


@Composable
fun DetailScreen(
    petId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPetById(petId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    image = data.image,
                    id = data.id,
                    name = data.name,
                    breed = data.breed,
                    description = data.description,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updatePet(id, state)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    name: String,
    breed: String,
    @DrawableRes image: Int,
    description: String,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .testTag("scrollToBottom")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = breed,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                )
            }
            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_home")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
        IconButton(
            onClick = {
                onFavoriteButtonClicked(id, isFavorite)
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 8.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("favorite_detail_button")
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!isFavorite) stringResource(R.string.add_favorite) else stringResource(R.string.delete_favorite),
                tint = if (!isFavorite) Color.Black else Color.Red
            )
        }
    }
}