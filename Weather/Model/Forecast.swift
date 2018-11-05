//
//  Forecast.swift
//  Weather
//
//  Created by Christian  Huang on 05/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import Foundation


struct ForecastResponse: Codable {
    let city: City
    let list: [Forecast]
}

struct Forecast: Codable {
    let dayTime: TimeInterval
    let main: WeatherData
    let weather: [Weather]
    let clouds: Clouds
    let wind: Wind
    let dayTimeText: String
    
    enum CodingKeys: String, CodingKey {
        case dayTime = "dt"
        case main
        case weather
        case clouds
        case wind
        case dayTimeText = "dt_txt"
    }
}
