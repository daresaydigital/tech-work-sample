//
//  LoadableJSONTest.swift
//  DarsayTechTestTests
//
//  Created by Farzaneh on 11/8/1401 AP.
//

import XCTest
@testable import DarsayTechTest
import Combine

final class LoadableJSONTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLoadableJSONwithPopular() {
        
        let mockService = MockService()
        var cancellables = Set<AnyCancellable>()

        mockService.getPopularMovies().sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testLoadableJSONwithTopRated() {
        
        let mockService = MockService()
        var cancellables = Set<AnyCancellable>()

        mockService.getTopRatedMovies().sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.count, 1)
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testLoadableJSONwithDetail() {
        
        let mockService = MockService()
        var cancellables = Set<AnyCancellable>()

        mockService.getMovieDetail(movieID: "").sinkToResult { result in
            
            switch result {
            case .success(let movie):
                XCTAssertEqual(movie.originalLanguage, "en")
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }
    
    func testLoadableJSONwithReview() {
        
        let mockService = MockService()
        var cancellables = Set<AnyCancellable>()

        mockService.getMovieReviews(movieID: "").sinkToResult { result in
            
            switch result {
            case .success(let list):
                XCTAssertEqual(list.results.first?.author, "Me")
            case .failure(let error):
                XCTFail(error.localizedDescription)
            }
        }.store(in: &cancellables)
    }

}
