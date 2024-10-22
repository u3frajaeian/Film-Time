package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.CreditItem
import io.filmtime.data.model.GeneralError.ApiError
import io.filmtime.data.model.GeneralError.NetworkError
import io.filmtime.data.model.GeneralError.UnknownError
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType
import io.filmtime.domain.tmdb.movies.GetMovieCreditUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class GetMovieCreditUseCaseImplTest {

  private lateinit var getMovieCreditUseCase: GetMovieCreditUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    getMovieCreditUseCase = GetMovieCreditUseCaseImpl(repository)
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
      `when`(repository.getCredit(movieId)).thenReturn(expectedResult)
      val result = getMovieCreditUseCase.invoke(movieId)

      assertTrue(result is Failure)
      verify(repository, times(i + 1)).getCredit(movieId)
      assertEquals(error, (result as Failure).error)
    }
  }

  @Test
  fun `call getMovieCredit and get credits as well`() = runBlocking {
    val movieId = 1
    val movieCredit = listOf(
      CreditItem(1,"ActorName","ProfilePoster")

    )
    val expectedResult = Success(movieCredit)
    `when`(repository.getCredit(movieId)).thenReturn(expectedResult)

    val result = getMovieCreditUseCase.invoke(movieId)

    assertTrue(result is Success)
    verify(repository).getCredit(movieId)
    assertEquals(expectedResult, result)
  }
}
