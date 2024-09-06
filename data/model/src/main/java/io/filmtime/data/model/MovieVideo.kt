package io.filmtime.data.model

data class MovieVideo(
  val name: String,
  val link: String? = null,
  val source: VideoSource? = null,
  val key: String,
  val posterUrl: String? = null,
)

enum class VideoSource {
  YouTube,
}
