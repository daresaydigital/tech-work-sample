//
//  TestPopularMoviesInteractor.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/2/22.
//

import XCTest
@testable import Movie_Application

class TestPopularMoviesInteractor: XCTestCase {

    var interactor: PopularMoviesInteractor!

    override func setUpWithError() throws {
        interactor = .init()
    }

    override func tearDownWithError() throws {
        interactor = nil
    }

    func testInteractorHasGetTopRatedMoviesMethod() throws {
        interactor.getPopularMovies(page: 0) { _ in
            return
        }
    }

    func testInteractorHasGetMovieImage() throws {
        interactor.getMovieImage(for: "") { _ in
            return
        }
    }

    func testInteractorHasGetMovieDetails() throws {
        interactor.getMovieDetails(id: 0) { _ in
            return
        }
    }

}
