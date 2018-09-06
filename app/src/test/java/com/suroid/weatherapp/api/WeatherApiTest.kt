package com.suroid.weatherapp.api

import com.suroid.weatherapp.models.remote.WeatherResponseModel
import com.suroid.weatherapp.util.RxImmediateSchedulerRule
import com.suroid.weatherapp.util.RxJavaTestUtil.getValue
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class WeatherApiTest {

    @Rule
    @JvmField
    val schedulers = RxImmediateSchedulerRule()

    private lateinit var service: WeatherApi

    private lateinit var mockWebServer: MockWebServer

    private val testKey = "abcdefgh"

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    var request = chain.request()
                    // Add constant params using okhttp interceptor
                    val url = request.url().newBuilder().addQueryParameter("key", testKey).build()
                    request = request.newBuilder().url(url).build()
                    chain.proceed(request)
                }.build()

        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(WeatherApi::class.java)
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getWeatherWithIdTest() {
        val id = 2673730
        enqueueResponse("city-response.json")
        val weatherResponseModel = getValue(service.getWeatherWithId(id))

        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, CoreMatchers.`is`("/weather?id=$id&key=$testKey"))

        Assert.assertThat<WeatherResponseModel>(weatherResponseModel, IsNull.notNullValue())
        Assert.assertThat(weatherResponseModel.id, CoreMatchers.`is`(id))
        Assert.assertThat(weatherResponseModel.getWeather()!!.id, CoreMatchers.`is`(803))
        Assert.assertThat(weatherResponseModel.name, CoreMatchers.`is`("Stockholm"))
    }

    @Test
    fun getWeatherWithLatLongTest() {
        val lat = 59.33
        val long = 18.07
        enqueueResponse("city-response.json")
        val weatherResponseModel = getValue(service.getWeatherWithLatLong(lat, long))

        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, CoreMatchers.`is`("/weather?lat=$lat&lon=$long&key=$testKey"))

        Assert.assertThat<WeatherResponseModel>(weatherResponseModel, IsNull.notNullValue())
        Assert.assertThat(weatherResponseModel.id, CoreMatchers.`is`(2673730))
        Assert.assertThat(weatherResponseModel.getWeather()!!.id, CoreMatchers.`is`(803))
        Assert.assertThat(weatherResponseModel.name, CoreMatchers.`is`("Stockholm"))
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        inputStream?.let {
            val source = Okio.buffer(Okio.source(inputStream))
            val mockResponse = MockResponse()
            mockWebServer.enqueue(
                    mockResponse
                            .setBody(source.readString(Charsets.UTF_8))
            )
        }

    }


}