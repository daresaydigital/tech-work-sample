//
//  TestTopRatedMoviesInteractor.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/1/22.
//

import XCTest
@testable import Movie_Application

final class TestTopRatedMoviesInteractor: XCTestCase {

    var interactor: TopRatedMoviesInteractor!

    override func setUpWithError() throws {
        interactor = .init()
    }

    override func tearDownWithError() throws {
        interactor = nil
    }

    func testInteractorHasGetTopRatedMoviesMethod() throws {
        interactor.getTopRatedMovies(page: 0) { _ in
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
