package io.filmtime.data.api.trakt

import io.filmtime.data.model.ExternalID
import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.Result
import io.filmtime.data.network.adapter.NetworkResponse
import io.filmtime.data.network.trakt.TraktSearchService
import javax.inject.Inject

internal class TraktSearchRemoteSourceImpl @Inject constructor(
  private val traktIDLookupService: TraktSearchService,
) : TraktSearchRemoteSource {

  override suspend fun getByTmdbId(id: Int, type: TraktMediaType): Result<Int, GeneralError> {
    return when (val result = traktIDLookupService.movieIDLookup(idType = "tmdb", id = id, type = type.queryName)) {
      is NetworkResponse.ApiError -> {
        val errorResponse = result.body
        Result.Failure(GeneralError.ApiError(errorResponse.error, result.code))
      }

      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.Success -> {
        val body = result.body ?: emptyList()
        val mediaId = body.find { response ->
          if (type == TraktMediaType.Movie) {
            response.movie?.ids?.tmdb == id
          } else {
            response.show?.ids?.tmdb == id
          }
        }?.let { response ->
          if (type == TraktMediaType.Movie) {
            response.movie?.ids?.trakt
          } else {
            response.show?.ids?.trakt
          }
        } ?: return Result.Failure(GeneralError.ApiError("Media ID not found", 404))
        return Result.Success(mediaId)
      }

      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
    }
  }

  override suspend fun getOtherWebsiteIds(id: Int, type: TraktMediaType): Result<ExternalID, GeneralError> {
    return when (val result = traktIDLookupService.movieIDLookup(idType = "tmdb", id = id, type = type.queryName)) {
      is NetworkResponse.ApiError -> {
        val errorResponse = result.body
        Result.Failure(GeneralError.ApiError(errorResponse.error, result.code))
      }

      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.Success -> {
        val body = result.body ?: emptyList()

        val mediaItem = body.find { response ->
          if (type == TraktMediaType.Movie) {
            response.movie?.ids?.tmdb == id
          } else {
            response.show?.ids?.tmdb == id
          }
        }

        val externalID = if (type == TraktMediaType.Movie) {
          val ids = mediaItem?.movie?.ids

          ExternalID(
            traktId = ids?.trakt.toString(),
            imdbId = ids?.imdb,
            tmdbId = ids?.tmdb.toString(),
            slug = ids?.slug,
          )
        } else {
          val ids = mediaItem?.show?.ids
          ExternalID(
            traktId = ids?.trakt.toString(),
            imdbId = ids?.imdb,
            tmdbId = ids?.tmdb.toString(),
            slug = ids?.slug,
          )
        }
        Result.Success(externalID)

      }

      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
    }
  }
}
