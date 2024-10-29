package io.filmtime.tv.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.filmtime.core.ui.common.componnents.LoadingVideoSectionRow
import io.filmtime.tv.R
import io.filmtime.tv.ui.component.ErrorScreen
import io.filmtime.tv.ui.component.MoviesRow

@Composable
fun HomeScreen(
  onTopBarVisibleChange: (Boolean) -> Unit,
) {
  val viewModel: HomeViewModel = hiltViewModel()
  val uiState by viewModel.state.collectAsStateWithLifecycle()
  val lazyColumnState = rememberLazyListState()
  val shouldShowTopBar by remember {
    derivedStateOf {
      lazyColumnState.firstVisibleItemIndex == 0 &&
        lazyColumnState.firstVisibleItemScrollOffset == 0
    }
  }
  LaunchedEffect(shouldShowTopBar) {
    onTopBarVisibleChange(shouldShowTopBar)
  }
  HomeScreenContent(
    homeUiState = uiState,
    onReload = viewModel::reload,
    lazyListState = lazyColumnState,
  )
}

@Composable
fun HomeScreenContent(
  sectionsCount: Int = 2,
  homeUiState: HomeUiState,
  onReload: () -> Unit,
  lazyListState: LazyListState = rememberLazyListState(),
) {
  when {
    homeUiState.isLoading -> LoadingVideoSectionRow(numberOfSections = sectionsCount)
    homeUiState.error != null -> {
      ErrorScreen(
        message = homeUiState.error,
        actionTitle = stringResource(R.string.btn_retry),
        onActionClick = onReload,
        modifier = Modifier.fillMaxSize(),
      )
    }

    else -> LazyColumn(
      state = lazyListState,
      contentPadding = PaddingValues(bottom = 150.dp),
    ) {
      items(homeUiState.videoSections) {
        MoviesRow(
          thumbnails = it.items,
          title = it.title,
        )
      }
    }
  }
}
