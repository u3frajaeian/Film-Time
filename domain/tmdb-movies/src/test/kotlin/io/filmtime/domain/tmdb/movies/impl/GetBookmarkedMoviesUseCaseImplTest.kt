package io.filmtime.domain.tmdb.movies.impl

import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Movie
import io.filmtime.domain.tmdb.movies.GetBookmarkedMoviesUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
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

class GetBookmarkedMoviesUseCaseImplTest {
  private lateinit var getBookmarkedMoviesUseCase: GetBookmarkedMoviesUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    getBookmarkedMoviesUseCase = GetBookmarkedMoviesUseCaseImpl(repository)
  }

  @Test
  fun `should return bookmarked movies from local db`(): Unit = runBlocking {
    val bookmarkedMovies = listOf(
      VideoThumbnail(VideoId(1, 0), "url1", "posterUrl", 2000, Movie),
      VideoThumbnail(VideoId(2, 0), "url2", "posterUrl2", 2000, Movie),
    )
    `when`(repository.getBookmarkedMovies()).thenReturn(flowOf(bookmarkedMovies))
    var result = getBookmarkedMoviesUseCase.invoke().toList()
    assertEquals(bookmarkedMovies, result[0])
    verify(repository).getBookmarkedMovies()
  }
}
