package com.daresaydigital.data.utils

import com.daresaydigital.core.utils.NetworkConstants
import com.daresaydigital.data.R
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class FakeServer {
  private val mockWebServer = MockWebServer()

  private val endpointSeparator = "/"
  private val popularMoviesPath = endpointSeparator + NetworkConstants.POPULAR_ENDPOINT
  private val notFoundResponse = MockResponse().setResponseCode(404)

  val baseEndpoint
    get() = mockWebServer.url(endpointSeparator)

  fun start() {
    mockWebServer.start(8080)
  }

  fun setHappyPopularMoviesPathDispatcher() {
    mockWebServer.dispatcher = object : Dispatcher() {
      override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return notFoundResponse

        return with(path) {
          when {
            startsWith(popularMoviesPath) -> {
              MockResponse().setResponseCode(200).setBody(JsonReader.getJson(R.raw.popular_movies))
            }
            else -> {
              notFoundResponse
            }
          }
        }
      }
    }
  }

  fun shutdown() {
    mockWebServer.shutdown()
  }
}