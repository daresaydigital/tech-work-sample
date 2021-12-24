package com.mousavi.hashem.mymoviesapp.data.remote

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.data.remote.dto.GenresDto
import com.mousavi.hashem.mymoviesapp.data.remote.dto.PageDataDto
import retrofit2.HttpException
import java.io.IOException

class NetworkDataSourceImpl(
    private val api: Api,
) : NetworkDataSource {

    override suspend fun getPopularMovies(
        language: String,
        page: Int,
    ): Either<PageDataDto, String> {
        return try {
            val popularMovies = api.getPopularMovies(language, page)
            Either.Success(popularMovies)
        } catch (e: HttpException) {
            Either.Error(error = e.message ?: "Error occurred")
        } catch (e: IOException) {
            Either.Error(error = e.message ?: "Check your internet connection!")
        } catch (e: Exception) {
            Either.Error(error = e.message ?: "Unknown error!")
        }
    }

    override suspend fun getGenres(): Either<GenresDto, String> {
        return try {
            val genresDto = api.getGenres()
            Either.Success(genresDto)
        } catch (e: HttpException) {
            Either.Error(error = e.message ?: "Error occurred")
        } catch (e: IOException) {
            Either.Error(error = e.message ?: "Check your internet connection!")
        } catch (e: Exception) {
            Either.Error(error = e.message ?: "Unknown error!")
        }
    }
}