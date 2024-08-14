package io.fimltime.data.tmdb.movies

import io.filmtime.data.model.VideoDetail
import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Movie

fun VideoDetail.toVideoThumbnail(): VideoThumbnail =
  VideoThumbnail(
    title = title,
    type = Movie,
    year = year,
    ids = VideoId(tmdbId = ids.tmdbId, traktId = ids.traktId),
    posterUrl = posterUrl,
  )
