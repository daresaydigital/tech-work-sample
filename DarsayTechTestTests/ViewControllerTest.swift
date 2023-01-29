//
//  DarsayTechTestTests.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import XCTest
@testable import DarsayTechTest

final class DarsayTechTestTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testMovieListViewControllerFetchTopRated() throws {
        
        let mockService = MockService()
        
        let viewController = MovieListBuilder.build(with: .init(movieNetworkAPIManager: mockService))

        viewController.viewModel.handle(action: .fetchTopRatedMovies)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertEqual(viewController.viewModel.state.topRatedList?.count, 1)
        XCTAssertEqual(viewController.viewModel.state.topRatedList?.first?.originalLanguage, "fa")
    }
    
    func testMovieListViewControllerFetchPopular() throws {
        
        let mockService = MockService()
        
        let viewController = MovieListBuilder.build(with: .init(movieNetworkAPIManager: mockService))

        viewController.viewModel.handle(action: .fetchPopularMovies)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertEqual(viewController.viewModel.state.popularList?.count, 1)
        XCTAssertEqual(viewController.viewModel.state.popularList?.first?.title, "Sample title for most popular movie")
    }
    
    func testMovieDetailViewControllerFetchDetail() throws {
        
        let mockService = MockService()
        
        let viewController = MovieDetailBuilder.build(with: .init(movieID: 0, movieNetworkAPIManager: mockService))

        viewController.viewModel.handle(action: .fetchDetail)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertNotNil(viewController.viewModel.state.movie)
    }
    
    func testMovieDetailViewControllerFetchReview() throws {
        
        let mockService = MockService()
        
        let viewController = MovieDetailBuilder.build(with: .init(movieID: 0, movieNetworkAPIManager: mockService))

        viewController.viewModel.handle(action: .fetchReviews)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertEqual(viewController.viewModel.state.reviewList?.first?.author, "Me")
    }

}
