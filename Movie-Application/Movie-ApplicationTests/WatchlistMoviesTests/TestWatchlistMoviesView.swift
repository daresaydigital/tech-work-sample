//
//  TestWatchlistMoviesView.swift
//  Movie-ApplicationTests
//
//  Created by Mohanna Zakizadeh on 5/2/22.
//

import XCTest
@testable import Movie_Application

final class TestWatchlistMoviesView: XCTestCase {

    var view: WatchlistMoviesView!
    // swiftlint: disable identifier_name
    var collectionViewDidSelectRowATCallsPresenterMovieSelectedExpectation: XCTestExpectation?
    var collectionViewNumberOfItemsCallsPresenterNumberOfMoviesExpectation: XCTestExpectation?

    override func setUpWithError() throws {
        let navigation = UIStoryboard(name: "WatchlistMovies",
                                      bundle: nil).instantiateInitialViewController() as? UINavigationController
        view = navigation?.topViewController as? WatchlistMoviesView
        view.presenter = self
        view.loadView()
        view.viewDidLoad()
    }

    override func tearDownWithError() throws {
        view = nil
    }

    func testViewHasWatchlistTitle() throws {
        // Given
        let title = "Watchlist"

        // When
        let viewTitle = view.navigationItem.title

        // Then
        XCTAssert(viewTitle == title)
    }

    func testViewHasLargeTitle() throws {
        // Given
        let prefersLargeTitles = true

        // When
        let viewPrefersLargeTitles = view.navigationController!.navigationBar.prefersLargeTitles

        // Then
        XCTAssert(prefersLargeTitles == viewPrefersLargeTitles)
    }

    func testViewHasNavigationSortButton() throws {
        XCTAssertNotNil(view.navigationItem.rightBarButtonItem)
    }

    func testViewHasNavigationSortButtonTitle() throws {
        // Given
        let title = "Sort"
        // When
        let viewNavigationButtonTitle = view.navigationItem.rightBarButtonItem!.title!
        // Then
        XCTAssert(title == viewNavigationButtonTitle)
    }

    func testCollectionViewHasDataSource() throws {
        XCTAssertNotNil(view.collectionView.dataSource)
    }

    func testCollectionViewHasDelegate() throws {
        XCTAssertNotNil(view.collectionView.delegate)
    }

    func testViewConformsToCollectionViewDataSource() throws {
        XCTAssertNotNil(view as? UICollectionViewDataSource)
    }

    func testViewConformsToCollectionViewDelegate() throws {
        XCTAssertNotNil(view as? UICollectionViewDelegate)
    }

    func testViewConformsToCollectionViewDelegateFlowLayout() {
        XCTAssertNotNil(view as? UICollectionViewDelegateFlowLayout)
    }

    func testCollectionViewHasACellWithCityCellID() throws {
        XCTAssertNotNil(view.collectionView.dequeueReusableCell(withReuseIdentifier: "MovieCell",
                                                                for: IndexPath(index: 1)))
    }

    func testCollectionViewDidSelectRowATCallsPresenterMovieSelected() throws {
        collectionViewDidSelectRowATCallsPresenterMovieSelectedExpectation =
        expectation(description: "expect presenter movieSelected to fullfill this expectation")
        view.collectionView(view.collectionView, didSelectItemAt: IndexPath(row: 0, section: 0))
        wait(for: [collectionViewDidSelectRowATCallsPresenterMovieSelectedExpectation!], timeout: 2)
    }

    func testCollectionViewNumberOfItemsCallsPresenterNumberOfMovies() throws {
        collectionViewNumberOfItemsCallsPresenterNumberOfMoviesExpectation =
        expectation(description: "expect presenter numberOfMovies fullfill this expectation")
        _ = view.collectionView(view.collectionView, numberOfItemsInSection: 0)
        wait(for: [collectionViewNumberOfItemsCallsPresenterNumberOfMoviesExpectation!], timeout: 2)
    }

}

extension TestWatchlistMoviesView: WatchlistMoviesPresenterViewInterface {
    func configureContextMenu(_ index: Int) -> UIContextMenuConfiguration {
        UIContextMenuConfiguration()
    }

    func viewDidLoad() {

    }

    func getMovieImage(index: Int) -> UIImage {
        UIImage()
    }

    func getMovieTitle(index: Int) -> String {
        ""
    }

    func movieSelected(at index: Int) {
        DispatchQueue.main.async {
            self.collectionViewDidSelectRowATCallsPresenterMovieSelectedExpectation?.fulfill()
        }
    }

    func deletefromWatchList(_ index: Int) {

    }

    func getWatchlistMovies() {

    }

    func deleteMovies() {

    }

    func alertRetryButtonDidTap(_ index: Int) {

    }

    func sortByDate() {

    }

    func sortByName() {

    }

    func sortByUserScore() {

    }

    func browseMoviesDidTap() {

    }

    var watchlistMovies: [CoreDataMovie] {
        [CoreDataMovie(title: "", poster: Data(), id: 0, date: Date(), voteAverage: 0)]
    }

    var numberOfMovies: Int {
        DispatchQueue.main.async {
            self.collectionViewNumberOfItemsCallsPresenterNumberOfMoviesExpectation?.fulfill()
        }
        return 1
    }

}
