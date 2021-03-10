//
//  MockNetworkManager.swift
//  WeatherTests
//
//  Created by Christian  Huang on 11/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation
@testable import Weather

class MockNetworkManager: WeatherNetworkManager {
    var apiKey: String
    var mockWeatherResponse: WeatherResponse?
    var mockForecastResponse: ForecastResponse?
    var mockForecastDailyResponse: ForecastDailyResponse?
    var mockError: String?
    
    required init(apiKey: String, environment: NetworkEnvironment) {
        self.apiKey = apiKey
    }
    
    func fetchWeather(latitude: Double, longitude: Double, completion: @escaping (WeatherResponse?, String?) -> ()) {
        completion(mockWeatherResponse, mockError)
    }
    
    func fetchForecast(latitude: Double, longitude: Double, completion: @escaping (ForecastResponse?, String?) -> ()) {
        completion(mockForecastResponse, mockError)
    }
    
    func fetchForecastDaily(latitude: Double, longitude: Double, completion: @escaping (ForecastDailyResponse?, String?) -> ()) {
        completion(mockForecastDailyResponse, mockError)
    }
    
    
}
