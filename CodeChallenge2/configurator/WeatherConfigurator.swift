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
    func current(based coordinate: (lat: Double, lon: Double), handler: @escaping (Forecast?) -> ()){
        worker.current(based: coordinate) { (weather) in
            handler(weather)
        }
    }
}
