//
//  ModelTest.swift
//  MovieTests
//
//  Created by Adrian Sergheev on 2021-03-30.
//

import XCTest

enum FailMessage: String {
    //    case modelShouldNotBeNil
    case modelDataShouldNotBeNil
}

class ModelTest: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    
    func loadJson(_ fileName: String) throws -> Data? {
        if let path = Bundle.main.path(forResource: fileName, ofType: "json") {
            do {
                let fileUrl = URL(fileURLWithPath: path)
                let data = try Data(contentsOf: fileUrl, options: .mappedIfSafe)
                return data
            } catch {
                XCTFail(FailMessage.modelDataShouldNotBeNil.rawValue)
                return nil
            }
        } else {
            XCTFail(FailMessage.modelDataShouldNotBeNil.rawValue)
            return nil
        }
    }
    
    func decodeModel<T: Decodable>(data: Data) throws -> T {
        return try JSONDecoder().decode(T.self, from: data)
    }
    
    func testModel<T: Decodable>(model: T.Type, jsonFilename: String) throws {
        if let data = try loadJson(jsonFilename) {
            let modelPayload: T = try decodeModel(data: data)
            XCTAssertNotNil(modelPayload)
        } else {
            XCTFail(FailMessage.modelDataShouldNotBeNil.rawValue)
        }
    }
    
    
    func testInitMoviePayload() throws {
        try testModel(model: MoviePayload.self, jsonFilename: "trending")
    }
    
    func testInitGenrePayload() throws {
        try testModel(model: GenrePayload.self, jsonFilename: "genre")
    }
    
    func testInitReviewPayload() throws {
        try testModel(model: ReviewPayload.self, jsonFilename: "reviews")
        
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
}
