//
//  ModelTest.swift
//  MovieTests
//
//  Created by Adrian Sergheev on 2021-03-30.
//

import XCTest
@testable import Movie

class ModelTest: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    
    func testInitMoviePayload() throws {
        try testModel(model: MoviePayload.self, jsonFilename: TestFilenames.trending.rawValue)
    }
    
    func testInitGenrePayload() throws {
        try testModel(model: GenrePayload.self, jsonFilename: TestFilenames.genre.rawValue)
    }
    
    func testInitReviewPayload() throws {
        try testModel(model: ReviewPayload.self, jsonFilename: TestFilenames.reviews.rawValue)
        
    }
    
}
