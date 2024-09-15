package com.daftech.eplclubs.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daftech.eplclubs.R
import com.daftech.eplclubs.ViewModelFactory
import com.daftech.eplclubs.di.Injection
import com.daftech.eplclubs.model.Club
import com.daftech.eplclubs.model.ClubData
import com.daftech.eplclubs.ui.common.UiState
import com.daftech.eplclubs.ui.theme.EPLClubsTheme


@Composable
fun DetailClubScreen(
    rewardId: Int,
    viewModel: DetailClubViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getClubById(rewardId)
            }
            is UiState.Success -> {
                val data = uiState.data
                ClubDetail(
                    club = data,
                    navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updatePlayer(id, state)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun ClubDetail(
    club: Club,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier) {

    var isFavorite = club.isFavorite == true

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(club.color.toColorInt()))
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = {
                        navigateBack()
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(CircleShape)
                        .size(32.dp)
                        .background(Color.White)
                        .testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
                Image(
                    painter = painterResource(club.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .align(Alignment.Center)
                        .height(200.dp)
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                IconButton(
                    onClick = {
                        onFavoriteButtonClicked(club.id, isFavorite)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .size(32.dp)
                        .background(Color.White)
                        .testTag("favorite_button")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) stringResource(R.string.remove_from_favorite) else stringResource(R.string.add_to_favorite),
                        tint = if (isFavorite) Color.Red else Color.Black
                    )
                }
            }
            Text(
                text = club.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            Text(
                text = "(${club.fullname})",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            DetailInfo("Nickname", club.nickname, Modifier.padding(top = 16.dp))
            DetailInfo("Founded", club.founded, Modifier.padding(top = 16.dp))
            DetailInfo("Ground", "${club.ground} (${club.capacity})", Modifier.padding(top = 4.dp))

            Divider(modifier = Modifier.padding(16.dp))
            Text(
                text = club.description,
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

        }
    }
}

@Composable
fun DetailInfo(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 8.dp)) {
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = content,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    EPLClubsTheme {
        ClubDetail(
            ClubData.clubs[0],
            navigateBack = {

            },
            onFavoriteButtonClicked = { id, state ->

            }
        )
    }
}