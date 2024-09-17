package io.filmtime.tv.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.filmtime.data.model.VideoType
import io.filmtime.tv.ui.detail.movie.movieDetailDestination
import io.filmtime.tv.ui.navigation.TabDestination.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeGraph

fun NavGraphBuilder.homeGraph(
  onFirstItemVisibleChange: (isVisible: Boolean) -> Unit,
  onThumbnailClick: (tmdbId: Int, type: VideoType) -> Unit = { _, _ -> },
) {
  navigation<HomeGraph>(
    startDestination = HomeScreen,
  ) {
    composable<HomeScreen> {
      HomeScreen(
        onFirstItemVisibleChange = onFirstItemVisibleChange,
        onThumbnailClick = onThumbnailClick,
      )
    }
    movieDetailDestination()
  }
}
