//
//  WeatherWorker.swift
//  CodeChallenge2
//
//  Created by Vincent Berihuete on 10/30/18.
//  Copyright © 2018 vincentchallenges. All rights reserved.
//

import Foundation
import Alamofire

struct WeatherWorker{
    
    private let URL = "http://worksample-api.herokuapp.com"
    private let API_KEY = ""
    
    private let decoder = JSONDecoder()
    
    
    /// Gets the current weather information from a remote API, based on a given coordinate
    ///
    /// - Parameters:
    ///   - coordinate: the given coordinate touple with a latitude and longitude
    ///   - handler: the callback that gives back the Weather object
    func current(based coordinate: (lat: Double, lon: Double), handler: @escaping (Weather?) -> ()){
        
        Alamofire.request("\(URL)/weather?lat=\(coordinate.lat)&lon=\(coordinate.lon)&key=\(API_KEY)", method: .get, parameters: nil, encoding: JSONEncoding.default, headers: nil).responseData { (http) in
            guard let data = http.result.value else {
                handler(nil)
                return
            }
            let weather: Weather? = try? JSONDecoder().decode(Weather.self, from: data)
            handler(weather)
        }
    }
    
    
    /// Gets the forecast information from a remote API, based on a given coordinate
    ///
    /// - Parameters:
    ///   - coordinate: the given coordinate touple with a latitude and longitude
    ///   - handler: the callback that gives back the Weather object
    func forecast(based coordinate: (lat: Double, lon: Double), handler: @escaping (Forecast?) -> ()){
        
        Alamofire.request("\(URL)/forecast?lat=\(coordinate.lat)&lon=\(coordinate.lon)&key=\(API_KEY)", method: .get, parameters: nil, encoding: JSONEncoding.default, headers: nil).responseData { (http) in
            guard let data = http.result.value else {
                handler(nil)
                return
            }
            let forecast: Forecast? = try? JSONDecoder().decode(Forecast.self, from: data)
            handler(forecast)
        }
    }
}
