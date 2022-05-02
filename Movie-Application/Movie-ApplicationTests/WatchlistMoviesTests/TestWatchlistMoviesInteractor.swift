//
//  TestWatchlistMoviesInteractor.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/2/22.
//

import XCTest
@testable import Movie_Application

class TestWatchlistMoviesInteractor: XCTestCase {
    
    var interactor: WatchlistMoviesInteractor!

    override func setUpWithError() throws {
        interactor = .init()
    }

    override func tearDownWithError() throws {
        interactor = nil
    }

    func testInteractorHasGetMovieDetails() throws {
        interactor.getMovieDetails(id: 0) { _ in
            return
        }
    }

}
