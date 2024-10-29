package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.GeneralError.ApiError
import io.filmtime.data.model.GeneralError.NetworkError
import io.filmtime.data.model.GeneralError.UnknownError
import io.filmtime.data.model.MovieCollection
import io.filmtime.data.model.Result.Failure
import io.filmtime.data.model.Result.Success
import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Movie
import io.filmtime.domain.tmdb.movies.GetMovieCollectionUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetMovieCollectionUseCaseImplTest {
  private lateinit var useCase: GetMovieCollectionUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    useCase = GetMovieCollectionUseCaseImpl(repository)
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
      `when`(repository.getCollection(movieId)).thenReturn(expectedResult)
      val result = useCase.invoke(movieId)

      TestCase.assertTrue(result is Failure)
      verify(repository, times(i + 1)).getCollection(movieId)
      assertEquals(error, (result as Failure).error)
    }
  }
  @Test
  fun `should return collection of selected MovieId`(): Unit = runBlocking {
    val movieId = 1
    val movieCollection = MovieCollection(
      id = 1, name = "movie1", overview = "overview", posterPath = "posterPath", backdropPath = "",
      parts = listOf(
        VideoThumbnail(
          VideoId(1, 0), "url1", "posterUrl", 2000, Movie,
        ),
        VideoThumbnail(VideoId(2, 0), "url2", "posterUrl2", 2000, Movie),
      ),
    )
    val expectedResult = Success(movieCollection)
    `when`(repository.getCollection(movieId)).thenReturn(expectedResult)
    val result = useCase.invoke(movieId)
    assertTrue(result is Success)
    assertEquals(expectedResult, result)
    verify(repository).getCollection(movieId)

  }
}
