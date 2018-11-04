//
//  Weather.swift
//  Weather
//
//  Created by Christian  Huang on 04/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation

enum DayTime: Int {
    case morning
    case noon
    case afternoon
    case night
}

struct WeatherResponse: Codable {
    let coord: Coordinate
    let weather: [Weather]
    let base: String
    let main: WeatherData
    let wind: Wind
    let clouds: Clouds
    //let rain: Rain?
    //let snow: Snow?
    let dt: TimeInterval
    let sys: SystemData
    let id: Int
    let name: String
}
struct Coordinate: Codable {
    let longitude: Double
    let latitude: Double
    
    enum CodingKeys: String, CodingKey {
        case longitude = "lon"
        case latitude = "lat"
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

struct Wind: Codable {
    let speed: Double
    let degree: Double
    
    enum CodingKeys: String, CodingKey {
        case speed
        case degree = "deg"
    }
}

struct Clouds: Codable {
    let cloudiness: Double
    
    enum CodingKeys: String, CodingKey {
        case cloudiness = "all"
    }
}

/*struct Rain: Codable {
    let rain3h: Double
    
    enum CodingKeys: String, CodingKey {
        case rain3h = "3h"
    }
}

struct Snow: Codable {
    let snow3h: Double
    
    enum CodingKeys: String, CodingKey {
        case snow3h = "3h"
    }
}*/

struct SystemData: Codable {
    let type: Int
    let id: Int
    let message: Double
    let country: String
    let sunrise: TimeInterval
    let sunset: TimeInterval

}
