package io.filmtime.domain.tmdb.movies.impl

import androidx.paging.PagingData
import io.filmtime.data.model.VideoId
import io.filmtime.data.model.VideoThumbnail
import io.filmtime.data.model.VideoType.Movie
import io.filmtime.domain.tmdb.movies.GetMoviesByGenreUseCase
import io.fimltime.data.tmdb.movies.TmdbMovieRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetMoviesByGenreUseCaseImplTest {
  private lateinit var useCase: GetMoviesByGenreUseCase

  @Mock
  private lateinit var repository: TmdbMovieRepository

  @Before
  fun setUp() {
//    val pagingConfig = PagingConfig(
//      pageSize = 20,
//      enablePlaceholders = false,
//    )
    MockitoAnnotations.openMocks(this)
    useCase = GetMoviesByGenreUseCaseImpl(repository)
  }

  @Test
  fun `should return collection of selected MovieId`(): Unit = runBlocking {
    val movieId = 1L
    val movies = listOf(
      VideoThumbnail(
        VideoId(1, 0), "url1", "posterUrl", 2000, Movie,
      ),
      VideoThumbnail(VideoId(2, 0), "url2", "posterUrl2", 2000, Movie),

      )

    val expectedResult = PagingData.from(movies)
    `when`(repository.moviesByGenre(movieId)).thenReturn(flowOf( expectedResult))
//    val result = useCase.invoke(movieId).toList()
//    val items = result[0]



  }
}
