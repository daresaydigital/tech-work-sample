//
//  ExecuterTest.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import XCTest
@testable import DarsayTechTest
import Combine

final class ExecuterTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testJsonExecuterWithPopularService() {
        var cancellables = Set<AnyCancellable>()
        
        let allJSONExecuter = MockHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allJSONExecuter)
        
        mockNetworkManager.getPopularMovies().sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testJsonExecuterWithTopRatedService() {
        var cancellables = Set<AnyCancellable>()
        
        let allJSONExecuter = MockHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allJSONExecuter)
        
        mockNetworkManager.getTopRatedMovies().sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testJsonExecuterWithMovieDetailService() {
        var cancellables = Set<AnyCancellable>()
        
        let allJSONExecuter = MockHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allJSONExecuter)
        
        mockNetworkManager.getMovieDetail(movieID: "").sinkToResult { result in
            
            switch result {
            case .success(let movie):
                XCTAssertEqual(movie.originalLanguage, "en")
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testJsonExecuterWithMovieReview() {
        var cancellables = Set<AnyCancellable>()
        
        let allJSONExecuter = MockHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allJSONExecuter)
        
        mockNetworkManager.getMovieReviews(movieID: "").sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    //================================ Test FailExecuter ====================================//
    
    func testFailExecuterWithMovieListVCForFetchPopular() {
        let allFailure = MockAllFailureHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allFailure)
        
        let viewController = MovieListBuilder.build(with: .init(movieNetworkAPIManager: mockNetworkManager))
        
        viewController.viewModel.handle(action: .fetchPopularMovies)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertNotNil(viewController.viewModel.state.error)
    }
    
    func testFailExecuterWithMovieListVCForFetchTopRated() {
        let allFailure = MockAllFailureHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allFailure)
        
        let viewController = MovieListBuilder.build(with: .init(movieNetworkAPIManager: mockNetworkManager))
        
        viewController.viewModel.handle(action: .fetchTopRatedMovies)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertNotNil(viewController.viewModel.state.error)
    }
    
    func testFailExecuterWithDetailVCForFetchDetail() {
        let allFailure = MockAllFailureHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allFailure)
        
        let viewController = MovieDetailBuilder.build(with: .init(movieID: 0, movieNetworkAPIManager: mockNetworkManager))
        
        viewController.viewModel.handle(action: .fetchDetail)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertNotNil(viewController.viewModel.state.error)
    }
    
    func testFailExecuterWithDetailVCForFetchReviews() {
        let allFailure = MockAllFailureHTTPRequestExecuter()
        
        let mockNetworkManager = MovieNetworkAPIManager(executer: allFailure)
        
        let viewController = MovieDetailBuilder.build(with: .init(movieID: 0, movieNetworkAPIManager: mockNetworkManager))
        
        viewController.viewModel.handle(action: .fetchReviews)
        let expectation = expectation(description: "Fetch data from service")

        viewController.releadList {
            expectation.fulfill()
        }
   
        wait(for: [expectation], timeout: 10.0)

        XCTAssertNotNil(viewController.viewModel.state.error)
    }
    
}
