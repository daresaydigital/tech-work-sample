//
//  CodeChallenge2Tests.swift
//  CodeChallenge2Tests
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import XCTest
@testable import CodeChallenge2

class CodeChallenge2Tests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testWeatherLoad(){
        let promise = expectation(description: "Weather not nil")
        WeatherConfigurator.shared.current(based: (lat: 38.8892686, lon: 77.05017599999996)) { (weather) in
            guard let _ = weather else{
                XCTFail("Error while getting weather MAKE SURE you set an API_KEY in WeatherWorker file")
                promise.fulfill()
                return
            }
            promise.fulfill()
        }
        waitForExpectations(timeout: 10, handler: nil)
    }
    
    func testForecastLoad(){
        let promise = expectation(description: "Forecast not nil")
        WeatherConfigurator.shared.forecast(based: (lat: 38.8892686, lon: 77.05017599999996)) { (forecast) in
            guard let forecast = forecast, forecast.list.count > 0 else{
                XCTFail("Error while getting forecast MAKE SURE you set an API_KEY in WeatherWorker file")
                promise.fulfill()
                return
            }
            promise.fulfill()
        }
        waitForExpectations(timeout: 10, handler: nil)
    }

}
