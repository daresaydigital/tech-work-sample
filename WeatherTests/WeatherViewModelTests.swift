//
//  WeatherViewModelTests.swift
//  WeatherTests
//
//  Created by Christian  Huang on 11/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import XCTest
@testable import Weather

class WeatherViewModelTests: XCTestCase {
    var weatherViewModel = WeatherViewModel()
    var networkManager = MockNetworkManager(apiKey: "testAPIKey", environment: .qa)

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        weatherViewModel.networkManager = networkManager
        weatherViewModel.location = Coordinate(longitude: 1, latitude: 2)
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testFetchWeatherError() {
        networkManager.mockWeatherResponse = nil
        networkManager.mockError = "This is an error"
        
        weatherViewModel.fetchWeather()
        
        XCTAssertEqual(weatherViewModel.status, networkManager.mockError)
    }
    
    func testFetchWeather() {
        let data = loadDataFromBundle(withName: "weather", extension: "json")
        let weatherResponse = try! JSONDecoder().decode(WeatherResponse.self, from: data)
        
        networkManager.mockWeatherResponse = weatherResponse
        networkManager.mockError = nil
        
        weatherViewModel.fetchWeather()
        
        XCTAssertNil(weatherViewModel.status)
        
        XCTAssertEqual(weatherViewModel.area, "Cideng")
        XCTAssertEqual(weatherViewModel.weather, "Rain")
        XCTAssertEqual(weatherViewModel.temperature, 30.22)
        XCTAssertEqual(weatherViewModel.summary, "Today: Rain currently. It's 30°; The high today was forecast as 32°.")
        XCTAssertEqual(weatherViewModel.timeString, "13:00")
        XCTAssertEqual(weatherViewModel.dayString, "Sunday")
        XCTAssertEqual(weatherViewModel.dayShortString, "Sun")
        XCTAssertEqual(weatherViewModel.temperatureMin, 28)
        XCTAssertEqual(weatherViewModel.temperatureMax, 32)
        XCTAssertEqual(weatherViewModel.sunrise, "05:25")
        XCTAssertEqual(weatherViewModel.sunriseTime, 19500)
        XCTAssertEqual(weatherViewModel.sunset, "17:48")
        XCTAssertEqual(weatherViewModel.sunsetTime, 64080)
        XCTAssertEqual(weatherViewModel.dayTime, .day)
        XCTAssertEqual(weatherViewModel.windSpeed, "4.1 mps")
        XCTAssertEqual(weatherViewModel.pressure, "1008 hPa")
        XCTAssertEqual(weatherViewModel.humidity, "78 %")
        XCTAssertEqual(weatherViewModel.icon, "http://openweathermap.org/img/w/10d.png")
        
    }
    
    func testUpdateWeatherWithForecast() {
        let data = loadDataFromBundle(withName: "forecast", extension: "json")
        let forecastResponse = try! JSONDecoder().decode(ForecastResponse.self, from: data)
        
        weatherViewModel.sunriseTime = 19500
        weatherViewModel.sunsetTime = 64080
        
        weatherViewModel.updateWeatherData(forecast: forecastResponse.list[2])
        
        XCTAssertEqual(weatherViewModel.weather, "Clouds")
        XCTAssertEqual(weatherViewModel.temperature, 26.71)
        XCTAssertEqual(weatherViewModel.timeString, "04:00")
        XCTAssertEqual(weatherViewModel.dayString, "Tuesday")
        XCTAssertEqual(weatherViewModel.dayShortString, "Tue")
        XCTAssertEqual(weatherViewModel.dayTime, .night)
        XCTAssertEqual(weatherViewModel.temperatureMin, 26.71)
        XCTAssertEqual(weatherViewModel.temperatureMax, 26.75)
        XCTAssertEqual(weatherViewModel.windSpeed, "2.64 mps")
        XCTAssertEqual(weatherViewModel.pressure, "1021 hPa")
        XCTAssertEqual(weatherViewModel.humidity, "100 %")
        XCTAssertEqual(weatherViewModel.icon, "http://openweathermap.org/img/w/03n.png")
    }
    
    func testUpdateWeatherWithForecastDaily() {
        let data = loadDataFromBundle(withName: "forecastDaily", extension: "json")
        let forecastDailyResponse = try! JSONDecoder().decode(ForecastDailyResponse.self, from: data)
        
        weatherViewModel.sunriseTime = 19500
        weatherViewModel.sunsetTime = 64080
        
        weatherViewModel.updateWeatherData(forecastDaily: forecastDailyResponse.list[3])
        
        XCTAssertEqual(weatherViewModel.weather, "Rain")
        XCTAssertEqual(weatherViewModel.temperature, 29.29)
        XCTAssertEqual(weatherViewModel.timeString, "11:00")
        XCTAssertEqual(weatherViewModel.dayString, "Thursday")
        XCTAssertEqual(weatherViewModel.dayShortString, "Thu")
        XCTAssertEqual(weatherViewModel.dayTime, .day)
        XCTAssertEqual(weatherViewModel.temperatureMin, 26.42)
        XCTAssertEqual(weatherViewModel.temperatureMax, 30.08)
        XCTAssertEqual(weatherViewModel.windSpeed, "0.55 mps")
        XCTAssertEqual(weatherViewModel.pressure, "1022 hPa")
        XCTAssertEqual(weatherViewModel.humidity, "94 %")
        XCTAssertEqual(weatherViewModel.icon, "http://openweathermap.org/img/w/10d.png")
    }
    
    

}
