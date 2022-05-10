//
//  TestWatchlistMoviesRouter.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/2/22.
//

import XCTest
@testable import Movie_Application

final class TestWatchlistMoviesRouter: XCTestCase {

    var router: WatchlistMoviesRouter!

    override func setUpWithError() throws {
        router = .init()
    }

    override func tearDownWithError() throws {
       router = nil
    }

    func testRouterHasShowMovieDetailsMethod() throws {
        router.showMovieDetails(MovieDetail(title: "", poster: "", id: 0,
                                            genres: [Genres(id: 0, name: "")],
                                            overview: nil, voteAverage: 0,
                                            releaseDate: "", reviewsCount: 0))
    }

    func testRouterHasShowPopulerMoviesMethod() throws {
        router.showPopularMovies()
    }

}
