//
//  TestWatchlistMoviesPresenter.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/2/22.
//

import XCTest
@testable import Movie_Application

class TestWatchlistMoviesPresenter: XCTestCase {

// swiftlint: disable identifier_name
    var presenter: WatchlistMoviesPresenter!

    var notificationCallsViewScrollToTopExpectation: XCTestExpectation?
    var presenterMovieSelectedCallsInteractorGetMoviesDetailsExpectation: XCTestExpectation?
    var presenterCallsSetWatchlistEmptyContainerisHiddenExpectation: XCTestExpectation?
    var presenterDeleteFromWatchlistCallsViewReloadCollectionViewExpectation: XCTestExpectation?

    override func setUpWithError() throws {
        presenter = .init()
        presenter.router = self
        presenter.view = self
        presenter.interactor = self

        presenter.movies = [CoreDataMovie(title: "", poster: Data(), id: 0, date: Date(), voteAverage: 0)]
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

    func testPresenterMovieSelectedCallsInteractorGetMoviesDetails() throws {
        presenterMovieSelectedCallsInteractorGetMoviesDetailsExpectation =
        expectation(description: "expect interactor getMovieDetails to fullfill this expectation")

        presenter.movieSelected(at: 0)
        wait(for: [presenterMovieSelectedCallsInteractorGetMoviesDetailsExpectation!], timeout: 1)
    }

    func testPresenterCallsSetWatchlistEmptyContainerisHidden() throws {
        presenterCallsSetWatchlistEmptyContainerisHiddenExpectation =
        expectation(description: "expect view setWatchlistEmptyContainerisHidden to fullfill this expectation")

        presenter.getWatchlistMovies()
        wait(for: [presenterCallsSetWatchlistEmptyContainerisHiddenExpectation!], timeout: 1)
    }

    func testPresenterDeleteFromWatchlistCallsViewReloadCollectionView() throws {
        presenterDeleteFromWatchlistCallsViewReloadCollectionViewExpectation =
        expectation(description: "expect view reloadCollectionView to fullfill this expectation")

        presenter.deletefromWatchList(0)
        wait(for: [presenterDeleteFromWatchlistCallsViewReloadCollectionViewExpectation!], timeout: 1)
    }

}

extension TestWatchlistMoviesPresenter: WatchlistMoviesViewInterface {
    func reloadCollectionView() {
        DispatchQueue.main.async {
            self.presenterDeleteFromWatchlistCallsViewReloadCollectionViewExpectation?.fulfill()
        }
    }

    func scrollToTop() {
        DispatchQueue.main.async {
            self.notificationCallsViewScrollToTopExpectation?.fulfill()
        }
    }

    func showError(with error: RequestError, index: Int) {

    }

    func setWatchlistEmptyContainerisHidden(to isHidden: Bool) {
        DispatchQueue.main.async {
            self.presenterCallsSetWatchlistEmptyContainerisHiddenExpectation?.fulfill()
        }
    }

}

extension TestWatchlistMoviesPresenter: WatchlistMoviesRouterInterface {
    func showMovieDetails(_ movie: MovieDetail) {

    }

    func showPopularMovies() {

    }

}

extension TestWatchlistMoviesPresenter: WatchlistMoviesInteractorInterface {
    func getMovieDetails(id: Int, completionHandler: @escaping MovieDetailsCompletionHandler) {
        DispatchQueue.main.async {
            self.presenterMovieSelectedCallsInteractorGetMoviesDetailsExpectation?.fulfill()
        }
    }

}
