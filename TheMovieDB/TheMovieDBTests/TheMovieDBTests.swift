//
//  TheMovieDBTests.swift
//  TheMovieDBTests
//
//  Created by Ali Sani on 12/11/21.
//

import XCTest
@testable import TheMovieDB

class TheMovieDBTests: XCTestCase {

    var viewModel: MovieDetailsViewModel!
    
    override func setUp() {
        super.setUp()
        viewModel = MovieDetailsViewModel(MovieDetailsService: MovieDetailsAPIServiceFake(), movieId: 0)
    }
    
    func testNumberOfRowsReturnsZeroWhenMovieDetailsIsNil() {
        viewModel.movieDetails = nil
        XCTAssert(viewModel.numberOfRows == 0)
    }
    
    func testNumberOfRowsReturnsOneWhenMovieDetailsIsNotNil() {
        viewModel.movieDetails = FakeModels.FakeMovieDetails
        XCTAssert(viewModel.numberOfRows == 1)
    }
    
    func testCellDataReturnsMovieDetailsForAnyIndexPath() {
        viewModel.movieDetails = FakeModels.FakeMovieDetails
        var result = viewModel.getCellData(for: IndexPath(row: 0, section: 0))
        XCTAssertEqual(result, FakeModels.FakeMovieDetails)
        
        result = viewModel.getCellData(for: IndexPath(row: 1, section: 1))
        XCTAssertEqual(result, FakeModels.FakeMovieDetails)
        
        result = viewModel.getCellData(for: IndexPath(row: 1, section: 0))
        XCTAssertEqual(result, FakeModels.FakeMovieDetails)
    }
    
    func testAsksForReloadWhenMovieDetailsIsSet() {
        let expectation = self.expectation(description: "loadingState")
        
        viewModel.onShouldReloadTableView = {
            expectation.fulfill()
        }
        
        viewModel.movieDetails = FakeModels.FakeMovieDetails
        waitForExpectations(timeout: 2, handler: nil)
    }
    
    func testMovieDetailsIsSetWhenAPICallSucceeded() {
        viewModel.viewDidLoad()
        XCTAssertEqual(viewModel.movieDetails, FakeModels.FakeMovieDetails)
    }
}
