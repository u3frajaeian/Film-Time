package io.fimltime.data.tmdb.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.filmtime.data.api.tmdb.TmdbMoviesRemoteSource
import io.filmtime.data.database.dao.BookmarksDao
import io.filmtime.data.database.dao.MovieDetailDao
import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.MovieCollection
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.Person
import io.filmtime.data.model.Result
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.data.model.VideoDetail
import io.filmtime.data.model.VideoSource.YouTube
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class TmdbMovieRepositoryImpl @Inject constructor(
  private val tmdbMoviesRemoteSource: TmdbMoviesRemoteSource,
  private val movieDao: MovieDetailDao,
  private val bookmarksDao: BookmarksDao,
) : TmdbMovieRepository {

  companion object {
    const val YOUTUBE_WATCH_PAGE = "https://youtube.com/watch?v="
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/{id}/hqdefault.jpg"
  }

  override suspend fun getMovieDetails(movieId: Int): Flow<Result<VideoDetail, GeneralError>> = flow {
//    val localData = movieDao.getMovieByTmdbId(movieId).firstOrNull()
//    if (localData != null) {
//      emit(Result.Success(localData.toMovie()))
//      // TODO: invalidate cache later
//      fetchMovieDetailsFromNetwork(movieId)
//    } else {
    val apiResult = fetchMovieDetailsFromNetwork(movieId)
    emit(apiResult)
//    }
  }

  private suspend fun fetchMovieDetailsFromNetwork(movieId: Int): Result<VideoDetail, GeneralError> {
    return when (val result = tmdbMoviesRemoteSource.movieDetails(movieId)) {
      is Result.Success -> {
        movieDao.storeMovie(result.data.toEntity())
        result
      }

      is Result.Failure -> result
    }
  }

  override suspend fun getTrendingMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.trendingMovies(1)

  override suspend fun getPopularMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.popularMovies(page = 1)

  override suspend fun getTopRatedMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.topRatedMovies(page = 1)

  override suspend fun getNowPlayingMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.nowPlayingMovies(page = 1)

  override suspend fun upcomingMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.upcomingMovies(page = 1)

  override fun moviesStream(movieListType: MovieListType): Flow<PagingData<VideoThumbnail>> =
    Pager(
      config = PagingConfig(
        pageSize = TmdbMoviesRemoteSource.PAGE_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = {
        MoviesPagingSource(
          tmdbMoviesRemoteSource = tmdbMoviesRemoteSource,
          movieListType = movieListType,
        )
      },
    ).flow

  override fun moviesByGenre(genreId: Long): Flow<PagingData<VideoThumbnail>> =
    Pager(
      config = PagingConfig(
        pageSize = TmdbMoviesRemoteSource.PAGE_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = {
        MoviesByGenrePagingSource(
          tmdbMoviesRemoteSource = tmdbMoviesRemoteSource,
          genreId = genreId,
        )
      },
    ).flow

  override suspend fun credits(movieId: Int): Result<List<Person>, GeneralError> =
    tmdbMoviesRemoteSource.credits(movieId)

  override suspend fun getSimilar(movieId: Int): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.getSimilar(movieId)

  override suspend fun getCollection(collectionId: Int): Result<MovieCollection, GeneralError> =
    tmdbMoviesRemoteSource.getCollection(collectionId)

  override suspend fun getBookmarkedMovies(): Flow<List<VideoThumbnail>> {
    return bookmarksDao.getAllBookmarksByType(Movie).map { bookmarks ->
      val movieList: MutableList<VideoThumbnail> = mutableListOf()
      for (id in bookmarks) {
        when (val result = fetchMovieDetailsFromNetwork(id.tmdbId)) {
          is Failure -> continue
          is Success -> movieList.add(result.data.toVideoThumbnail())
        }
      }
      movieList
    }
  }

  override suspend fun getMovieVideos(movieId: Int): Result<List<MovieVideo>, GeneralError> {
    val videos = tmdbMoviesRemoteSource.getMovieVideos(movieId)

    return videos.mapSuccess { data ->
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
