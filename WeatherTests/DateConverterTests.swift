//
//  DateConverterTests.swift
//  WeatherTests
//
//  Created by Christian  Huang on 12/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import XCTest
@testable import Weather

class DateConverterTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }

    func testTimeIntervalToDayString() {
        let tests: [TimeInterval] = [1541430000, 1541476800, 1541563200, 1541649600, 1541736000]
        let results: [String] = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        
        for i in 0..<tests.count {
            let result = DateConverter.timeIntervalToDayString(tests[i])
            XCTAssertEqual(result, results[i])
        }
    }
    
    func testTimeIntervalToDayShortString() {
        let tests: [TimeInterval] = [1541430000, 1541476800, 1541563200, 1541649600, 1541736000]
        let results: [String] = ["Mon", "Tue", "Wed", "Thu", "Fri"]
        
        for i in 0..<tests.count {
            let result = DateConverter.timeIntervalToDayShortString(tests[i])
            XCTAssertEqual(result, results[i])
        }
    }
    
    func testTimeIntervalToHourString() {
        let tests: [TimeInterval] = [1541430000, 1541473200, 1541484000, 1541494800, 1541505600]
        let results: [String] = ["22", "10", "13", "16", "19"]
        
        for i in 0..<tests.count {
            let result = DateConverter.timeIntervalToHourString(tests[i])
            XCTAssertEqual(result, results[i])
        }
    }
    
    func testTimeIntervalToHourMinuteString() {
        let tests: [TimeInterval] = [1541430060, 1541473440, 1541484480, 1541495520, 1541506560]
        let results: [String] = ["22:01", "10:04", "13:08", "16:12", "19:16"]
        
        for i in 0..<tests.count {
            let result = DateConverter.timeIntervalToHourMinuteString(tests[i])
            XCTAssertEqual(result, results[i])
        }
    }
    
    func testDateConverter() {
        let tests: [TimeInterval] = [1541430060, 1541473440, 1541484480, 1541495520, 1541506560]
        let results: [TimeInterval] = [79260, 36240, 47280, 58320, 69360]
        
        for i in 0..<tests.count {
            let result = DateConverter.timeIntervalToDayTimeInterval(tests[i])
            XCTAssertEqual(result, results[i])
        }
    }

}
