package io.filmtime.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbVideosResponse(
  val id: Long,
  val results: List<VideoData>,
)

@Serializable
data class VideoData(
  @SerialName("iso_639_1")
  val iso639_1: String,
  @SerialName("iso_3166_1")
  val iso3166_1: String,
  val name: String,
  val key: String,
  val site: Site,
  val size: Long,
  val type: Type,
  val official: Boolean,
  @SerialName("published_at")
  val publishedAt: String,
  val id: String,
)

@Serializable
enum class Site(val value: String) {
  @SerialName("YouTube")
  YouTube("YouTube"),
}

@Serializable
enum class Type(val value: String) {
  @SerialName("Behind the Scenes")
  BehindTheScenes("Behind the Scenes"),

  @SerialName("Clip")
  Clip("Clip"),

  @SerialName("Featurette")
  Featurette("Featurette"),

  @SerialName("Teaser")
  Teaser("Teaser"),

  @SerialName("Trailer")
  Trailer("Trailer"),
}
