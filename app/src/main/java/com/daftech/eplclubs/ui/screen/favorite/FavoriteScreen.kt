package com.daftech.eplclubs.ui.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daftech.eplclubs.R
import com.daftech.eplclubs.ViewModelFactory
import com.daftech.eplclubs.di.Injection
import com.daftech.eplclubs.model.Club
import com.daftech.eplclubs.ui.common.UiState
import com.daftech.eplclubs.ui.components.EmptyList
import com.daftech.eplclubs.ui.components.ListClubs
import com.daftech.eplclubs.ui.screen.home.HomeViewModel


@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,

    ) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteClubs()
            }
            is UiState.Success -> {
                FavoriteContent(
                    listClub = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    listClub: List<Club>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column {
        if (listClub.isNotEmpty()) {
            ListClubs(
                listClub,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                message = stringResource(R.string.mssg_empty_data_favorite),
                modifier = modifier
                    .testTag("EmptyList")
            )
        }
    }
}