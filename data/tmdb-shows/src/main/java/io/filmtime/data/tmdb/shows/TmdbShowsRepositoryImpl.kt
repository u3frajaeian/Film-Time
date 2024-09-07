package io.filmtime.data.tmdb.shows

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.filmtime.data.api.tmdb.TmdbShowsRemoteSource
import io.filmtime.data.database.dao.BookmarksDao
import io.filmtime.data.model.EpisodeThumbnail
import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.Person
import io.filmtime.data.model.Result
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.data.model.VideoDetail
import io.filmtime.data.model.VideoSource.YouTube
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class TmdbShowsRepositoryImpl @Inject constructor(
  private val tmdbShowsRemoteSource: TmdbShowsRemoteSource,
  private val bookmarksDao: BookmarksDao,
) : TmdbShowsRepository {

  companion object {
    const val YOUTUBE_WATCH_PAGE = "https://youtube.com/watch?v="
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/{id}/hqdefault.jpg"
  }

  override suspend fun showDetails(showId: Int): Result<VideoDetail, GeneralError> =
    tmdbShowsRemoteSource.showDetails(showId)

  override suspend fun trendingShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.trendingShows(page = 1)

  override suspend fun popularShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.popularShows(page = 1)

  override suspend fun topRatedShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.topRatedShows(page = 1)

  override suspend fun onTheAirShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.onTheAirShows(page = 1)

  override suspend fun airingTodayShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.airingTodayShows(page = 1)

  override fun showsStream(
    type: ShowListType,
  ): Flow<PagingData<VideoThumbnail>> =
    Pager(
      config = PagingConfig(
        pageSize = TmdbShowsRemoteSource.PAGE_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = {
        ShowsPagingSource(
          tmdbShowsRemoteSource = tmdbShowsRemoteSource,
          showListType = type,
        )
      },
    ).flow

  override fun showsByGenre(genreId: Long): Flow<PagingData<VideoThumbnail>> =
    Pager(
      config = PagingConfig(
        pageSize = TmdbShowsRemoteSource.PAGE_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = {
        ShowsByGenrePagingSource(
          tmdbShowsRemoteSource = tmdbShowsRemoteSource,
          genreId = genreId,
        )
      },
    ).flow

  override suspend fun credits(movieId: Int): Result<List<Person>, GeneralError> =
    tmdbShowsRemoteSource.credits(movieId)

  override suspend fun similar(movieId: Int): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.similar(movieId)

  override suspend fun episodesBySeason(showId: Int, seasonNumber: Int): Result<List<EpisodeThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.episodesBySeason(showId, seasonNumber)

  override suspend fun getBookmarkedShows(): Flow<List<VideoThumbnail>> {
    return bookmarksDao.getAllBookmarksByType(Show).map { bookmarks ->
      val showsList = mutableListOf<VideoThumbnail>()
      for (bookmark in bookmarks) {
        when (val detail = showDetails(bookmark.tmdbId)) {
          is Failure -> continue
          is Success -> showsList.add(detail.data.toVideoThumbnail())
        }
      }
      showsList
    }
  }

  override suspend fun getVideos(showId: Int): Result<List<MovieVideo>, GeneralError> {
    val result = tmdbShowsRemoteSource.getShowVideos(showId)

    return result.mapSuccess { data ->
      data.map {
        val source = when (it.source) {
          YouTube -> YOUTUBE_WATCH_PAGE + it.key
          null -> null
        }
        val poster = when (it.source) {
          YouTube -> YOUTUBE_THUMBNAIL_URL.replace("{id}", it.key)
          null -> null
        }
        it.copy(link = source, posterUrl = poster)
      }
    }
  }
}
