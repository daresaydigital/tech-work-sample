//
//  Weather.swift
//  Weather
//
//  Created by Christian  Huang on 04/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation

struct WeatherResponse: Codable {
    let coordinate: Coordinate
    let weather: [Weather]
    let base: String
    let main: WeatherData
    let wind: Wind
    let clouds: Clouds
    //let rain: Rain?
    //let snow: Snow?
    let dayTime: TimeInterval
    let system: SystemData
    let id: Int
    let name: String
    
    enum CodingKeys: String, CodingKey {
        case coordinate = "coord"
        case weather
        case base
        case main
        case wind
        case clouds
        case dayTime = "dt"
        case system = "sys"
        case id
        case name
    }
}

struct Weather: Codable {
    let id: Int
    let main: String
    let description: String
    let icon: String
}

struct WeatherData: Codable {
    let temperature: Double
    let pressure: Int
    let humidity: Int
    let temperatureMin: Double
    let temperatureMax: Double
    let seaLevel: Double?
    let groundLevel: Double?
    
    enum CodingKeys: String, CodingKey {
        case temperature = "temp"
        case pressure
        case humidity
        case temperatureMin = "temp_min"
        case temperatureMax = "temp_max"
        case seaLevel = "sea_level"
        case groundLevel = "grnd_level"
    }
}
