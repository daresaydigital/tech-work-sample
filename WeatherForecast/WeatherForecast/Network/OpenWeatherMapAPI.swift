//
//  OpenWeatherMapAPI.swift
//  WeatherForecast
//
//  Created by Iciar Novo Fernandez on 09/05/2018.
//  Copyright © 2018 Icicleta. All rights reserved.
//

import Foundation

extension URL {
    func withQueries(_ queries: [String: String]) -> URL? {
        var components = URLComponents(url: self, resolvingAgainstBaseURL: true)
        components?.queryItems = queries.compactMap { URLQueryItem(name: $0.0, value: $0.1) }
        return components?.url
    }
}

typealias WeatherForecastJSON = [String: Any]

struct OpenWeatherMapAPI {

    static func getWeatherForecastJSON(country: String, completion: @escaping (WeatherForecastJSON?) -> Void) {
        let WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather"
        let query: [String: String] = [
            "appid": Bundle.main.infoDictionary!["API"]! as! String,
            "q": country
        ]

        let url = URL(string: WEATHER_URL)!.withQueries(query)
        guard let unwrappedURL = url else { print("Error unwrapping URL"); return }
        
        //fetching the data from the url
        let dataTask = URLSession.shared.dataTask(with: unwrappedURL) { data, response, error in

            guard let unwrappedData = data else { print("Error unwrapping data"); return }
            
            do {
                let responseJSON = try JSONSerialization.jsonObject(with: unwrappedData, options: []) as? WeatherForecastJSON
                completion(responseJSON)
            } catch {
                print("Could not get API data. \(error), \(error.localizedDescription)")
            }
        }
        dataTask.resume()
    }
}

