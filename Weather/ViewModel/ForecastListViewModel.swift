//
//  ForecastListViewModel.swift
//  Weather
//
//  Created by Christian  Huang on 04/11/18.
//  Copyright © 2018 Christian Huang. All rights reserved.
//

import UIKit

class ForecastListViewModel: NSObject {
    var forecastList: Dynamic<[ForecastViewModel]> = Dynamic([])
    var forecastCount: Int {
        return forecastList.value.count
    }
    
    var networkManager: WeatherNetworkManager!
    
    
}

extension ForecastListViewModel {
    func fetchForecast(coordinate: Coordinate) {
        networkManager.fetchForecast(latitude: coordinate.latitude, longitude: coordinate.longitude) { (forecastResponse, error) in
            if let error = error {
                print("Network Error: \(error)")
            } else {
                self.processForecastResponse(forecastResponse!)
            }
        }
    }
}

extension ForecastListViewModel {
    private func processForecastResponse(_ forecastResponse: ForecastResponse) {
        forecastList.value = forecastResponse.list.map({ forecast -> ForecastViewModel in
            return ForecastViewModel(forecast: forecast)
        })
        
    }
}
