//
//  WeatherConfigurator.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation

struct WeatherConfigurator {
    
    static let shared = WeatherConfigurator()
    
    private let worker = WeatherWorker()
    
    
    
    /// Gets the current weather information based on a given coordinate
    ///
    /// - Parameters:
    ///   - coordinate: the given coordinate touple with a latitude and longitude
    ///   - handler: the callback that gives back the Weather object
    func current(based coordinate: (lat: Double, lon: Double), handler: @escaping (Weather?) -> ()){
        worker.current(based: coordinate) { (weather) in
            handler(weather)
        }
    }
    
    /// Gets the forecast information, based on a given coordinate
    ///
    /// - Parameters:
    ///   - coordinate: the given coordinate touple with a latitude and longitude
    ///   - handler: the callback that gives back the Weather object
    func forecast(based coordinate: (lat: Double, lon: Double), handler: @escaping (Forecast?) -> ()){
        worker.forecast(based: coordinate){ (forecast) in
            handler(forecast)
        }
    }
    
    
    /// Groups a given Forecast per day
    ///
    /// - Parameter forecast: The forecast
    /// - Returns: An array of GroupForecast
    func group(forecast: Forecast) -> [GroupForecast]{
        return worker.group(forecast: forecast)
    }
    
    
    /// Provides the background image name based on a given weather
    ///
    /// - Returns: The bg image name
    func background(based weather: Weather) -> String{
        return worker.background(based: weather)
    }
    
}
