//
//  ViewModelTest.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/9/1401 AP.
//

import XCTest
@testable import DarsayTechTest
import Combine
final class ViewModelTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testFavoriteViewModel() {
        
        let favoriteViewModel = FavoriteListViewModel(configuration: .init())
        
        if FavoriteStorage.currentList.isEmpty {
            XCTAssertEqual(favoriteViewModel.state.favoriteList, nil)
        } else {
            XCTAssertEqual(favoriteViewModel.state.favoriteList?.count, favoriteViewModel.state.favoriteList?.count)
        }
    }
    
    func testMovieListViewModelFetchTopRated() throws {
        
        let mockService = MockService()
        
        let viewModel = MovieListViewModel(configuration: .init(movieNetworkAPIManager: mockService))

        let statePublisher = viewModel.statePublisher.collect(1).first()
        viewModel.handle(action: .fetchTopRatedMovies)
        
        let state = try awaitPublisher(statePublisher).last
        
        XCTAssertEqual(state?.topRatedList?.count, 1)
        XCTAssertEqual(state?.topRatedList?.first?.originalLanguage, "fa")
    }
    
    func testMovieListViewControllerFetchPopular() throws {

        let mockService = MockService()

        let viewModel = MovieListViewModel(configuration: .init(movieNetworkAPIManager: mockService))

        let statePublisher = viewModel.statePublisher.collect(1).first()
        viewModel.handle(action: .fetchPopularMovies)
       
        let state = try awaitPublisher(statePublisher).last
        
        XCTAssertEqual(state?.popularList?.count, 1)
        XCTAssertEqual(state?.popularList?.first?.title, "Sample title for most popular movie")
    }
    
    func testMovieDetailViewControllerFetchDetail() throws {

        let mockService = MockService()

        let viewModel = MovieDetailViewModel(configuration: .init(movieID: 0, movieNetworkAPIManager: mockService))

        let statePublisher = viewModel.statePublisher.collect(1).first()
        viewModel.handle(action: .fetchDetail)
        
        let state = try awaitPublisher(statePublisher).last
        
        XCTAssertNotNil(state?.movie)
    }
    
    func testMovieDetailViewControllerFetchReview() throws {

        let mockService = MockService()

        let viewModel = MovieDetailViewModel(configuration: .init(movieID: 0, movieNetworkAPIManager: mockService))

        let statePublisher = viewModel.statePublisher.collect(1).first()
       
        viewModel.handle(action: .fetchReviews)
        
        let state = try awaitPublisher(statePublisher).last
       
        XCTAssertEqual(state?.reviewList?.first?.author, "Me")
    }
    
}
