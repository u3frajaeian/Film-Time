package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.GeneralError.ApiError
import io.filmtime.data.model.GeneralError.NetworkError
import io.filmtime.data.model.GeneralError.UnknownError
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.data.model.VideoDetail
import io.filmtime.data.model.VideoGenre
import io.filmtime.data.model.VideoId
import io.filmtime.domain.tmdb.movies.GetMovieDetailsUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class GetMovieDetailsUseCaseImplTest {
  private lateinit var getMoviesDetailUseCase: GetMovieDetailsUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    getMoviesDetailUseCase = GetMovieDetailsUseCaseImpl(repository)
  }

  @Test
  fun `should return detail of selected movie`() = runBlocking {
    val movieId = 0
    val movieDetail = VideoDetail(
      ids = VideoId(traktId = 0, tmdbId = 1),
      title = "movie",
      year = 2024,
      budget = null,
      genres = listOf(
        VideoGenre(id = 1, "horror"),
        VideoGenre(id = 2, "game"),
      ),
      status = "release",
      releaseDate = "2024",
      runtime = null,
      tagline = null,
      coverUrl = "coverUrl",
      posterUrl = "posterUrl",
      homePage = null,
      networks = null,
      collectionId = null,
      isWatched = null,
      seasonsNumber = null,
      description = "dec",
      originalLanguage = null,
      spokenLanguages = listOf(),
    )
    `when`(repository.getMovieDetails(movieId)).thenReturn(flowOf(Success(movieDetail)))
    val result = getMoviesDetailUseCase.invoke(movieId).toList()
    assertTrue(result[0] is Success)
    verify(repository).getMovieDetails(movieId)
    assertEquals(movieDetail, (result[0] as Success).data)
  }

  @Test
  fun `should return error when repository returns failure`() = runBlocking {
    val movieId = 0
    val errorStates = listOf(
      NetworkError,
      UnknownError(Throwable()),
      ApiError("", 0),
    )

    errorStates.forEachIndexed { i, error ->
      val expectedResult = Failure(error)
      `when`(repository.getMovieDetails(movieId)).thenReturn(flowOf(expectedResult))
      val result = getMoviesDetailUseCase.invoke(movieId).toList()

      assertTrue(result[0] is Failure)
      verify(repository, times(i + 1)).getMovieDetails(movieId)
      assertEquals(error, (result[0] as Failure).error)
    }
  }
}
