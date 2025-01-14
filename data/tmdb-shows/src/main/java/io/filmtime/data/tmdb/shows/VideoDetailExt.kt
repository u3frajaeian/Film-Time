package io.filmtime.data.tmdb.shows

import io.filmtime.data.model.VideoDetail
import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Show

fun VideoDetail.toVideoThumbnail(): VideoThumbnail =
  VideoThumbnail(
    title = title,
    type = Show,
    year = year,
    ids = VideoId(tmdbId = ids.tmdbId, traktId = ids.traktId),
    posterUrl = posterUrl,
  )
