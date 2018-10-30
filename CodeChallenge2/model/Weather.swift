//
//  Weather.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation

struct Weather: Codable {
    
    
    /// The location of the retrieved weather information
    var name: String?
    var base: String?
    
    /// The weather description information
    var weather: [WeatherForecast]
    
    /// The main data containing the temperature
    var main: MainForecast
    var wind: WindForecast
    var date: Double
    var sys: SysForecast
    var showingDetail = false
    
    enum CodingKeys: String, CodingKey {
        case date = "dt"
        case name
        case base
        case weather
        case main
        case wind
        case sys
    }
    
}

struct WeatherForecast: Codable{
    var main: String
    var description: String
    var icon: String
}

struct MainForecast: Codable{
    var temp: Double
    var pressure: Double
    var humidity: Int
    var tempMin: Double
    var tempMax: Double

    enum CodingKeys: String, CodingKey{
        case tempMin = "temp_min"
        case tempMax = "temp_max"
        case temp
        case pressure
        case humidity
    }
}

struct WindForecast: Codable {
    var speed: Double
    var deg: Double
}

struct SysForecast: Codable{
    var country: String?
    var sunrise: Double?
    var sunset: Double?
}
