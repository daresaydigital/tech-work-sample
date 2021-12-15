//
//  Test_HomeServices.swift
//  Daresay MoviesTests
//
//  Created by Emad Bayramy on 12/15/21.
//

import XCTest
@testable import DaMovies_Dev

final class HomeServicesTest: XCTestCase {
    
    var sut: HomeService?
    var moviesJson: Data?
    
    override func setUp() {
        let bundle = Bundle(for: type(of: self))
        autoreleasepool {
            if let path = bundle.path(forResource: "Movies", ofType: "json") {
                do {
                    let data = try Data(contentsOf: URL(fileURLWithPath: path), options: .mappedIfSafe)
                    self.moviesJson = data
                } catch {
                    
                }
            }
        }
    }
    
    override func tearDown() {
        sut = nil
    }
    
    func test_getMovies() {
        
        // Given
        let urlSessionMock = URLSessionMock()
        urlSessionMock.data = moviesJson
        let mockRequestManager = RequestManagerMock(session: urlSessionMock, validator: MockResponseValidator())
        sut = HomeService(requestManager: mockRequestManager)
        let expectation = XCTestExpectation(description: "Async Movies test")
        var movies: MovieArrayModel = []
        
        // When
        sut?.getMovies(page: 0, completionHandler: { (result) in
            defer {
                expectation.fulfill()
            }
            switch result {
            case .success(let data):
                movies = data.results!
            case .failure:
                XCTFail()
            }
        })
        
        // Then
        print(movies.count)
        wait(for: [expectation], timeout: 5)
        XCTAssertTrue(movies.count == 20)
    }
}
