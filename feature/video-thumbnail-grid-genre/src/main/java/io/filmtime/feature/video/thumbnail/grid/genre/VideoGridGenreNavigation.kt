package io.filmtime.feature.video.thumbnail.grid.genre

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import io.filmtime.core.ui.navigation.DestinationRoute
import io.filmtime.core.ui.navigation.composable
import io.filmtime.data.model.VideoType

private const val ROUTE_PATH = "genre-grid"
private const val VIDEO_TYPE_ARG = "video_type"
private const val GENRE_ID_ARG = "genre_id"
private const val GENRE_NAME_ARG = "genre_name"

fun NavGraphBuilder.videoThumbnailGridGenreScreen(
  rootRoute: DestinationRoute,
  onMovieClick: (DestinationRoute, tmdbId: Int) -> Unit,
  onShowClick: (DestinationRoute, tmdbId: Int) -> Unit,
  onBack: () -> Unit,
) {
  composable(
    route = "${rootRoute.route}/$ROUTE_PATH/{$VIDEO_TYPE_ARG}/{${GENRE_ID_ARG}}/{$GENRE_NAME_ARG}",
    arguments = listOf(
      navArgument(GENRE_ID_ARG) {
        type = NavType.LongType
      },
      navArgument(VIDEO_TYPE_ARG) {
        type = NavType.EnumType(VideoType::class.java)
      },
      navArgument(GENRE_NAME_ARG) {
        type = NavType.StringType
      },
    ),
  ) {
    VideoThumbnailGridByGenreScreen(
      onMovieClick = { onMovieClick(rootRoute, it) },
      onShowClick = { onShowClick(rootRoute, it) },
      onBack = onBack,
    )
  }
}

fun NavController.navigateVideoGridByGenre(
  rootRoute: DestinationRoute,
  genreId: Long,
  genreName: String,
  videoType: VideoType,
) {
  navigate("${rootRoute.route}/$ROUTE_PATH/$videoType/$genreId/$genreName")
}

internal class VideoThumbnailGridArgs(
  val videoType: VideoType,
  val genreId: Long,
  val genreName: String,
) {
  constructor(savedStateHandle: SavedStateHandle) : this(
    videoType = savedStateHandle[VIDEO_TYPE_ARG] ?: throw IllegalArgumentException("Video type not found"),
    genreId = savedStateHandle[GENRE_ID_ARG] ?: throw IllegalArgumentException("Genre Id not found"),
    genreName = savedStateHandle[GENRE_NAME_ARG] ?: throw IllegalArgumentException("Genre Name not found"),
  )
}
