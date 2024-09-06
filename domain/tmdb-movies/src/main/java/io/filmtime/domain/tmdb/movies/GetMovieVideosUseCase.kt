package io.filmtime.domain.tmdb.movies

import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.Result

interface GetMovieVideosUseCase {

  suspend operator fun invoke(movieId: Int): Result<List<MovieVideo>, GeneralError>
}
