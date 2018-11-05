//
//  ForecastDaily.swift
//  Weather
//
//  Created by Christian  Huang on 05/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


struct ForecastDailyResponse: Codable {
    let city: City
    let list: [ForecastDaily]
}

struct ForecastDaily: Codable {
    let dayTime: TimeInterval
    let temperature: Temperature
    let pressure: Double
    let humidity: Double
    let weather: [Weather]
    let speed: Double
    let degree: Double
    let clouds: Double
    let rain: Double
    
    enum CodingKeys: String, CodingKey {
        case dayTime = "dt"
        case temperature = "temp"
        case pressure
        case humidity
        case weather
        case speed
        case degree = "deg"
        case clouds
        case rain
    }
}
