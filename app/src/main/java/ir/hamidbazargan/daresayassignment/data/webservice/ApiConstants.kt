package ir.hamidbazargan.daresayassignment.data.webservice

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/"
    private const val URL_VERSION = "/3/"
    private const val API_KEY = "c213d77560ed80975d41a41325597dce"
    private const val LANGUAGE = "en-US"
    private const val QUERIES = "?api_key=${API_KEY}&language=${LANGUAGE}"
    const val POPULAR_ENDPOINT = "${URL_VERSION}movie/popular${QUERIES}"
    const val TOP_RATED_ENDPOINT = "${URL_VERSION}movie/top_rated${QUERIES}"
    const val MOVIE_ENDPOINT = "${URL_VERSION}movie/{id}${QUERIES}"
}