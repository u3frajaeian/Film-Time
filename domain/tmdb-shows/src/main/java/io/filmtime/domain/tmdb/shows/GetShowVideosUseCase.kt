package io.filmtime.domain.tmdb.shows

import io.filmtime.data.model.GeneralError
import io.filmtime.data.model.MovieVideo
import io.filmtime.data.model.Result

interface GetShowVideosUseCase {

  suspend operator fun invoke(movieId: Int): Result<List<MovieVideo>, GeneralError>
}
