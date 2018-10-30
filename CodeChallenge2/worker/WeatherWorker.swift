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
    private let API_KEY = "FILL_THIS_API_KEY"
    
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
    
    /// Groups a given Forecast per day
    ///
    /// - Parameter forecast: The forecast
    /// - Returns: An array of GroupForecast
    func group(forecast: Forecast) -> [GroupForecast]{
        var result = [GroupForecast]()
        
        var previousWeather : Weather?
        
        var nGroup : GroupForecast?
        //        forecast.list.forEach { (weather) in
        for (i, weather) in forecast.list.enumerated(){
            if i == 0 {
                nGroup = GroupForecast(weather: weather, laterOn: [])
                previousWeather = weather
            }else if getDayOfMonth(of: previousWeather!.date) == getDayOfMonth(of: weather.date) {
                //same day
                nGroup?.laterOn.append(weather)
                previousWeather = weather
                if i == forecast.list.count - 1, let group = nGroup{
                    result.append(group)
                }
            }else{
                result.append(nGroup!)
                nGroup = GroupForecast(weather: weather, laterOn: [])
                previousWeather = weather
            }
        }
        
        return result
    }
    
    
    
    /// Gives back the day of the month of a given timestamp
    ///
    /// - Parameter timestamp: The long reprentation of the date
    /// - Returns: An integer value representing the day of the mont 1 ~= 31
    private func getDayOfMonth(of timestamp: Double) -> Int{
        return Calendar.current.component(.day, from: Date(timeIntervalSince1970: timestamp))
    }
    
    
    /// Provides the background image name based on a given weather
    ///
    /// - Returns: The bg image name
    func background(based weather: Weather) -> String{
        //        weather.weather[0].icon
        let icon = weather.weather[0].icon
        
        switch icon[icon.startIndex...icon.index(icon.startIndex, offsetBy: 1)] {
        case "01":
            return "weather_bg_sunny2"
        case "02":
            return "weather_bg_default"
        case "03":
            return "weather_bg_cloudy"
        case "10":
            return "weather_bg_rain"
        case "11":
            return "weather_bg_thunder"
        case "13":
            return "weather_bg_snow"
        default:
            return "weather_bg_default"
        }
    }
}
