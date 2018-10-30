//
//  Weather.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation

struct Forecast: Codable {
    
    var name: String
    var base: String
    var weather: [Weather]
    var main: MainForecast
    var wind: WindForecast
    var date: Int64
    var sys: SysForecast
    
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

struct Weather: Codable{
    var main: String
    var description: String
    var icon: String
}

struct MainForecast: Codable{
    var temp: Double
    var pressure: Int
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
    var country: String
    var sunrise: Int64
    var sunset: Int64
}
