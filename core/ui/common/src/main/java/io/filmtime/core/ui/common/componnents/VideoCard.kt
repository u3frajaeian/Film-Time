package io.filmtime.core.ui.common.componnents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.filmtime.core.ui.common.componnents.placeholder.PlaceholderHighlight
import io.filmtime.core.ui.common.componnents.placeholder.fade
import io.filmtime.core.ui.common.componnents.placeholder.placeholder
import io.filmtime.data.model.MovieVideo

@Composable
fun VideoCard(
  video: MovieVideo,
  placeHolderVisible: Boolean,
  onClick: (() -> Unit)? = null,
) {
  val placeholderHighlight = PlaceholderHighlight.fade()
  Box(
    modifier = Modifier
      .clickable { onClick?.invoke() },
  ) {
    AsyncImage(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9f)
        .placeholder(
          visible = placeHolderVisible,
          highlight = placeholderHighlight,
        ),
      model = video.posterUrl,
      contentDescription = video.link,
      contentScale = ContentScale.Crop,
    )

    Text(
      modifier = Modifier
        .padding(horizontal = 12.dp)
        .placeholder(
          visible = placeHolderVisible,
          highlight = placeholderHighlight,
        ),
      text = video.name,
    )
  }
}
