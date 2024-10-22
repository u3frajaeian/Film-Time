package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.GeneralError.ApiError
import io.filmtime.data.model.GeneralError.NetworkError
import io.filmtime.data.model.GeneralError.UnknownError
import io.filmtime.data.model.Result
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType
import io.filmtime.domain.tmdb.movies.GetSimilarUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class GetSimilarUseCaseImplTest {
  private lateinit var getSimilarUseCase: GetSimilarUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    getSimilarUseCase = GetSimilarUseCaseImpl(repository)
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
      `when`(repository.getSimilar(movieId)).thenReturn(expectedResult)
      val result = getSimilarUseCase.invoke(movieId)

      assertTrue(result is Failure)
      verify(repository, times(i + 1)).getSimilar(movieId)
      assertEquals(error, (result as Failure).error)

    }

  }

  @Test
  fun `call getSimilar and get similar as well`() = runBlocking {
    val movieId = 1
    val similarMovies = listOf(
      VideoThumbnail(VideoId(1, 0), "url1", "posterUrl", 2000, VideoType.Movie),
      VideoThumbnail(VideoId(2, 0), "url2", "posterUrl2", 2000, VideoType.Movie),
    )
    val expectedResult = Success(similarMovies)
    `when`(repository.getSimilar(movieId)).thenReturn(expectedResult)

    val result = getSimilarUseCase.invoke(movieId)

    assertTrue(result is Success)
    verify(repository).getSimilar(movieId)
    assertEquals(expectedResult, result)
  }
}
