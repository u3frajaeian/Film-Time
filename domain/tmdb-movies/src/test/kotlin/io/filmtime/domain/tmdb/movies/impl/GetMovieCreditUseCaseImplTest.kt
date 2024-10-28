package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.GeneralError.ApiError
import io.filmtime.data.model.GeneralError.NetworkError
import io.filmtime.data.model.GeneralError.UnknownError
import io.filmtime.data.model.Person
import io.filmtime.data.model.PersonType
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.domain.tmdb.movies.GetMovieCreditsUseCase
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

  private lateinit var getMovieCreditUseCase: GetMovieCreditsUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    getMovieCreditUseCase = GetMovieCreditsUseCaseImpl(repository)
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
      `when`(repository.credits(movieId)).thenReturn(expectedResult)
      val result = getMovieCreditUseCase.invoke(movieId)

      assertTrue(result is Failure)
      verify(repository, times(i + 1)).credits(movieId)
      assertEquals(error, (result as Failure).error)
    }
  }

  @Test
  fun `call getMovieCredit and get credits as well`() = runBlocking {
    val movieId = 1
    val movieCredit = listOf(
      Person(1, "ActorName", "ProfilePoster","", PersonType.Cast),

    )
    val expectedResult = Success(movieCredit)
    `when`(repository.credits(movieId)).thenReturn(expectedResult)

    val result = getMovieCreditUseCase.invoke(movieId)

    assertTrue(result is Success)
    verify(repository).credits(movieId)
    assertEquals(expectedResult, result)
  }
}
