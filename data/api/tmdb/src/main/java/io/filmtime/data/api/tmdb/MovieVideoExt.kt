package io.filmtime.data.api.tmdb

import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.VideoSource
import io.filmtime.data.network.model.Site
import io.filmtime.data.network.model.Site.YouTube
import io.filmtime.data.network.model.VideoData

fun VideoData.toVideos(): MovieVideo =
  MovieVideo(
    key = key,
    name = name,
    source = site.toSource(),
  )

fun Site.toSource(): VideoSource =
  when (this) {
    YouTube -> VideoSource.YouTube
  }
