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
}
