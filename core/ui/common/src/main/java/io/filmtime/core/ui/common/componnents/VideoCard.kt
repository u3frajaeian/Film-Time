package io.filmtime.core.ui.common.componnents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.filmtime.core.designsystem.theme.PreviewFilmTimeTheme
import io.filmtime.core.designsystem.theme.ThemePreviews
import io.filmtime.core.ui.common.componnents.placeholder.PlaceholderHighlight
import io.filmtime.core.ui.common.componnents.placeholder.fade
import io.filmtime.core.ui.common.componnents.placeholder.placeholder
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.VideoSource.YouTube

@Composable
fun VideoCard(
  video: MovieVideo,
  placeHolderVisible: Boolean,
  onClick: ((String) -> Unit)? = null,
) {
  val placeholderHighlight = PlaceholderHighlight.fade()
  Box(
    modifier = Modifier
      .aspectRatio(16f / 9f)
      .clip(RoundedCornerShape(8.dp))
      .then(
        if (video.link != null && onClick != null) {
          Modifier.clickable {
            onClick.invoke(video.link!!)
          }
        } else {
          Modifier
        },
      ),
  ) {
    AsyncImage(
      modifier = Modifier
        .aspectRatio(16f / 9f)
        .placeholder(
          visible = placeHolderVisible,
          highlight = placeholderHighlight,
        ),
      model = video.posterUrl,
      contentDescription = video.link,
      contentScale = ContentScale.Crop,
    )
  }
}

@ThemePreviews
@Composable
private fun PreviewVideoCard() {
  PreviewFilmTimeTheme {
    VideoCard(
      video = MovieVideo(
        name = "The Batman",
        posterUrl = "https://image.tmdb.org/t/p/w500/5VJSIAhSn4qUq3FjV3j6Uz5Zc4c.jpg",
        link = "https://www.youtube.com/watch?v=IhYDiZ0fF5Y",
        source = YouTube,
        key = "",
      ),
      placeHolderVisible = false,
    )
  }
}
