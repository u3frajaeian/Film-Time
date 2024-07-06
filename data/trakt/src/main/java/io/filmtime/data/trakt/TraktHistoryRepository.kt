package io.filmtime.data.trakt

import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.Result
import io.filmtime.data.model.TraktEpisodeHistory
import io.filmtime.data.model.TraktMovieHistory

interface TraktHistoryRepository {

  suspend fun isMovieWatched(tmdbId: Int): Result<TraktMovieHistory, GeneralError>

  suspend fun isShowWatched(tmdbId: Int, seasonNumber: Int): Result<Map<Int, List<TraktEpisodeHistory>>, GeneralError>

  suspend fun addToHistory(traktId: Int): Result<Unit, GeneralError>

  suspend fun removeFromHistory(traktId: Int): Result<Unit, GeneralError>
}
