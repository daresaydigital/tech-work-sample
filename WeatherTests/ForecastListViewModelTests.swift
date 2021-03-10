//
//  ForecastListViewModelTests.swift
//  WeatherTests
//
//  Created by Christian  Huang on 12/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import XCTest
@testable import Weather

class ForecastListViewModelTests: XCTestCase {
    var forecastListViewModel = ForecastListViewModel()
    var networkManager = MockNetworkManager(apiKey: "testAPIKey", environment: .qa)

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        forecastListViewModel.networkManager = networkManager
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testFetchForecastError() {
        networkManager.mockForecastResponse = nil
        networkManager.mockError = "This is an error"
        
        let coordinate = Coordinate(longitude: 1, latitude: 2)
        forecastListViewModel.fetchForecast(coordinate: coordinate)
        
        XCTAssertEqual(forecastListViewModel.status, networkManager.mockError)
    }
    
    func testFetchForecast() {
        let data = loadDataFromBundle(withName: "forecast", extension: "json")
        let forecastResponse = try! JSONDecoder().decode(ForecastResponse.self, from: data)
        
        networkManager.mockForecastResponse = forecastResponse
        networkManager.mockError = nil
        
        let coordinate = Coordinate(longitude: 1, latitude: 2)
        forecastListViewModel.fetchForecast(coordinate: coordinate)
        
        XCTAssertNil(forecastListViewModel.status)
        
        XCTAssertEqual(forecastListViewModel.forecastList.value.count, 40)
        
    }

}
