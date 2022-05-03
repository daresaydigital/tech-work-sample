//
//  TestPopularMoviesPresenter.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/2/22.
//

import XCTest
@testable import Movie_Application

class TestPopularMoviesPresenter: XCTestCase {

    var presenter: PopularMoviesPresenter!
    // swiftlint: disable identifier_name
    var notificationCallsViewScrollToTopExpectation: XCTestExpectation?
    var getPopularMoviesCallsInteractorGetTopMoviesExpectation: XCTestExpectation?
    var getMovieImageCallsInteractorGetMovieImageExpectation: XCTestExpectation?
    var presenterMovieSelectedCallsInteractorGetMoviesDetails: XCTestExpectation?

    override func setUpWithError() throws {
        presenter = .init()
        presenter.router = self
        presenter.view = self
        presenter.interactor = self
        presenter.movies = [Movie(title: "", poster: "", id: 0, voteAverage: 0)]
    }

    override func tearDownWithError() throws {
        presenter.view = nil
        presenter.interactor = nil
        presenter.router = nil
        presenter.movies = nil
        presenter = nil
    }

    func testNotificationCallsViewScrollToTop() throws {
        notificationCallsViewScrollToTopExpectation =
        expectation(description: "expect to view scroll to top fullfill this expectation")
        NotificationCenter.default.post(name: TabBarViewContorller.tabBarDidTapNotification, object: nil)
        wait(for: [notificationCallsViewScrollToTopExpectation!], timeout: 1)
    }

    func testGetTopRatedMoviesCallsInteractorGetTopMovies() throws {
        getPopularMoviesCallsInteractorGetTopMoviesExpectation =
        expectation(description: "expect interactor getTopRatedMovies to fullfill this expectation")
        presenter.getPopularMovies()
        wait(for: [getPopularMoviesCallsInteractorGetTopMoviesExpectation!], timeout: 1)
    }

    func testGetMovieImageCallsInteractorGetMovieImage() throws {
        getMovieImageCallsInteractorGetMovieImageExpectation =
        expectation(description: "expect interactor getMovieImage to fullfill this expectation")
        presenter.getMovieImage(index: 0) { (_) in
            return
        }
        wait(for: [getMovieImageCallsInteractorGetMovieImageExpectation!], timeout: 1)
    }

    func testPresenterGetMovieTitleReturnsString() throws {
        XCTAssertNotNil(presenter.getMovieTitle(index: 0))
    }

    func testPresenterMovieSelectedCallsInteractorGetMoviesDetails() throws {
        presenterMovieSelectedCallsInteractorGetMoviesDetails =
        expectation(description: "expect interactor getMovieDetails to fullfill this expectation")
        presenter.movieSelected(at: 0)
        wait(for: [presenterMovieSelectedCallsInteractorGetMoviesDetails!], timeout: 1)
    }

    func testPresenterHasAddToWatchListMethod() throws {
        presenter.addToWatchList(index: 0, imageData: Data())
    }

    func testPresenterHasNumberOfMovies() throws {
        XCTAssertNotNil(presenter.numberOfMovies)
    }

    func testPresenterHasTopRatedMovies() throws {
        XCTAssert(type(of: presenter.popularMovies) == [Movie].self)
    }

}

extension TestPopularMoviesPresenter: PopularMoviesViewInterface {
    func showError(with error: RequestError) {

    }

    func reloadCollectionView() {

    }

    func scrollToTop() {
        DispatchQueue.main.async {
            self.notificationCallsViewScrollToTopExpectation?.fulfill()
        }
    }

}

extension TestPopularMoviesPresenter: PopularMoviesInteractorInterface {
    func getPopularMovies(page: Int, completionHandler: @escaping MoviesCompletionHandler) {
        DispatchQueue.main.async {
            self.getPopularMoviesCallsInteractorGetTopMoviesExpectation?.fulfill()
        }
    }

    func getMovieImage(for path: String, completion: @escaping (UIImage) -> Void) {
        DispatchQueue.main.async {
            self.getMovieImageCallsInteractorGetMovieImageExpectation?.fulfill()
        }
    }

    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler) {
        DispatchQueue.main.async {
            self.presenterMovieSelectedCallsInteractorGetMoviesDetails?.fulfill()
        }
    }

}

extension TestPopularMoviesPresenter: PopularMoviesRouterInterface {
    func showMovieDetails(_ movie: MovieDetail) {

    }

}
