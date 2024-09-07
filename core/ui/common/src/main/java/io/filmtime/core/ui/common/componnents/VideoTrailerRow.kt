package io.filmtime.core.ui.common.componnents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.filmtime.core.ui.common.R
import io.filmtime.data.model.MovieVideo

@Composable
fun VideoTrailerRow(
  modifier: Modifier = Modifier,
  onClick: ((String) -> Unit)?,
  items: List<MovieVideo>,
  isLoading: Boolean,
) {
  Column(
    modifier = modifier,
  ) {
    Text(
      text = stringResource(id = R.string.core_ui_trailers_title),
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    )
    if (isLoading) {
      CircularProgressIndicator(
        modifier = Modifier
          .fillMaxWidth()
          .wrapContentSize(),
      )
    } else if (items.isNotEmpty()) {
      LazyRow(
        modifier = Modifier
          .height(180.dp)
          .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        items(items) { item ->
          VideoCard(
            video = item,
            placeHolderVisible = false,
            onClick = onClick,
          )
        }
      }
    }
  }
}
