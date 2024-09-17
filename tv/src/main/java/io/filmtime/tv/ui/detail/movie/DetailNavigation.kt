package io.filmtime.tv.ui.detail.movie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(val videoId: Int)

fun NavGraphBuilder.movieDetailDestination() {
  composable<MovieDetail> {
    MovieDetailScreen(modifier = Modifier.fillMaxSize())
  }
}

fun NavController.navigateToMovieDetail(tmdbId: Int) {
  navigate(MovieDetail(tmdbId))
}
