package io.filmtime.domain.tmdb.shows.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.filmtime.domain.tmdb.shows.GetBookmarkedShowsUseCase
import io.filmtime.domain.tmdb.shows.GetEpisodesBySeasonUseCase
import io.filmtime.domain.tmdb.shows.GetShowCreditsUseCase
import io.filmtime.domain.tmdb.shows.GetShowDetailsUseCase
import io.filmtime.domain.tmdb.shows.GetShowVideosUseCase
import io.filmtime.domain.tmdb.shows.GetShowsByGenreUseCase
import io.filmtime.domain.tmdb.shows.GetShowsListUseCase
import io.filmtime.domain.tmdb.shows.GetSimilarShowsUseCase
import io.filmtime.domain.tmdb.shows.GetTrendingShowsUseCase
import io.filmtime.domain.tmdb.shows.ObserveShowsStreamUseCase
import io.filmtime.domain.tmdb.shows.impl.GetBookmarkedShowsUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetEpisodesBySeasonUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetShowCreditsUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetShowDetailsUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetShowVideosUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetShowsByGenreUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetShowsListUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetSimilarShowsUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.GetTrendingShowsUseCaseImpl
import io.filmtime.domain.tmdb.shows.impl.ObserveShowsStreamUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TmdbShowsDomainModule {

  @Binds
  abstract fun bindGetTrendingShowsUseCase(impl: GetTrendingShowsUseCaseImpl): GetTrendingShowsUseCase

  @Binds
  abstract fun bindGetShowDetailsUseCase(impl: GetShowDetailsUseCaseImpl): GetShowDetailsUseCase

  @Binds
  abstract fun bindObserveShowsStreamUseCase(impl: ObserveShowsStreamUseCaseImpl): ObserveShowsStreamUseCase

  @Binds
  abstract fun bindGetShowsListUseCase(impl: GetShowsListUseCaseImpl): GetShowsListUseCase

  @Binds
  abstract fun bindGetShowCredit(impl: GetShowCreditsUseCaseImpl): GetShowCreditsUseCase

  @Binds
  abstract fun bindGetSimilar(impl: GetSimilarShowsUseCaseImpl): GetSimilarShowsUseCase

  @Binds
  abstract fun bindGetShowsBySeason(impl: GetEpisodesBySeasonUseCaseImpl): GetEpisodesBySeasonUseCase

  @Binds
  abstract fun bindGetShowsByGenreUseCase(impl: GetShowsByGenreUseCaseImpl): GetShowsByGenreUseCase

  @Binds
  abstract fun bindGetBookmarkedShowsUseCase(impl: GetBookmarkedShowsUseCaseImpl): GetBookmarkedShowsUseCase

  @Binds
  abstract fun bindGetShowVideosUseCase(impl: GetShowVideosUseCaseImpl): GetShowVideosUseCase
}
